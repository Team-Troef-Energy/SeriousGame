package nl.hu.serious_game.config;

import nl.hu.serious_game.presentation.filters.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// https://www.baeldung.com/spring-security-firebase-authentication
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    //private static final String[] WHITELISTED_API_ENDPOINTS = {};

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    public SecurityConfiguration(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authManager -> {
                    authManager
                            //.requestMatchers(HttpMethod.POST, WHITELISTED_API_ENDPOINTS)
                            //    .permitAll()
                            .anyRequest()
                                .authenticated();
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
