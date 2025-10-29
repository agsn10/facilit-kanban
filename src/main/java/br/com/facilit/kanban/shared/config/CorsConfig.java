package br.com.facilit.kanban.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8043") // ajuste para o host do Swagger UI
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}