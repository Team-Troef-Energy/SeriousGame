package nl.hu.serious_game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cors.allowed-origins}")
    private String allowedCorsOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println(allowedCorsOrigins);

        registry.addMapping("/**")
                .allowedOrigins(allowedCorsOrigins.split(";"))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
