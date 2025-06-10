package nl.hu.serious_game.application;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import nl.hu.serious_game.domain.User;
import nl.hu.serious_game.domain.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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
                UserRole.USER //getUserRoleFromFirestore(authentication.getName())
        );
    }

    private UserRole getUserRoleFromFirestore(String userId) {
        try {
            // TODO what happens if this document/key is not found?
            // If the collection is not found an exception should be thrown
            String roleKey = firestore.collection("users").document(userId).get().get().getString("role");
            if (roleKey == null) {
                return UserRole.USER;
            }
            return UserRole.fromKey(roleKey);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error retrieving document from Firestore", e);
        }
    }
}
