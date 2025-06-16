package nl.hu.serious_game.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// https://www.baeldung.com/spring-security-firebase-authentication
@Configuration
public class FirebaseAuthentication {
    @Value("${firebase.keyfile}")
    private String privateKeyPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (privateKeyPath == null || privateKeyPath.isBlank()) {
            privateKeyPath = "firebase-key.json";
        }
        InputStream credentials = new ByteArrayInputStream(Files.readAllBytes(Path.of(privateKeyPath)));
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();
        return FirebaseApp.initializeApp(firebaseOptions);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
