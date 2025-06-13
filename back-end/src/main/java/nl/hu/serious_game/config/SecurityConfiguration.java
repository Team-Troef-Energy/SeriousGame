package nl.hu.serious_game.config;

import nl.hu.serious_game.presentation.filters.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// https://www.baeldung.com/spring-security-firebase-authentication
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    public SecurityConfiguration(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authManager -> {
                    authManager.anyRequest().permitAll();
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // CSRF protection blocks all POST requests without a CSRF token
        //noinspection removal
        http.csrf().disable();

        return http.build();
    }
}
