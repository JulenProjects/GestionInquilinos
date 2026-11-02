package com.example.sistemaInquilinos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// Le decimos a Spring que esta clase contiene configuración del proyecto
@Configuration
public class CorsConfig {

    // Registramos el filtro CORS como un componente de Spring
    @Bean
    public CorsFilter corsFilter() {

        // Creamos el objeto que define las reglas CORS
        CorsConfiguration config = new CorsConfiguration();

        // Permite enviar cookies y cabeceras de autenticación (necesario para JWT)
        config.setAllowCredentials(true);

        // Permite peticiones desde cualquier origen (tu archivo HTML, Postman, etc.)
        config.addAllowedOriginPattern("*");

        // Permite cualquier cabecera HTTP (incluida Authorization donde va el token JWT)
        config.addAllowedHeader("*");

        // Permite cualquier método HTTP (GET, POST, PUT, DELETE...)
        config.addAllowedMethod("*");

        // Aplicamos esta configuración a todos los endpoints del proyecto ( /** = todo)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // Devolvemos el filtro listo para que Spring lo use en cada petición
        return new CorsFilter(source);
    }
}