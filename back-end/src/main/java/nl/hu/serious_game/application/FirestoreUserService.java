package nl.hu.serious_game.application;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import nl.hu.serious_game.domain.User;
import nl.hu.serious_game.domain.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/// Reads the user ID from Firebase authentication credentials, which is stored in the name field, and looks up the user
/// document in Firestore.
/// The user's role is stored in the users collection.
@Service
public class FirestoreUserService implements UserService {
    private final Firestore firestore;

    public FirestoreUserService(FirebaseApp firebaseApp) {
        firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    @Override
    public User getUser(Authentication authentication) {
        return new User(
                authentication.getName(),
                getUserRoleFromFirestore(authentication.getName())
        );
    }

    private UserRole getUserRoleFromFirestore(String userId) {
        try {
            // If the collection is not found an exception should be thrown
            DocumentSnapshot userDocument = firestore.collection("users").document(userId).get().get();
            if (!userDocument.exists()) {
                return UserRole.ANONYMOUS;
            }
            String roleKey = userDocument.getString("role");
            if (roleKey == null) {
                return UserRole.ANONYMOUS;
            }
            return UserRole.fromKey(roleKey);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error retrieving document from Firestore", e);
        }
    }
}
