package com.example.sistemaInquilinos.seguridad;

import com.example.sistemaInquilinos.seguridad.filtro.filtrarToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * CONFIGURACIÓN PRINCIPAL DE SEGURIDAD DEL PROYECTO.
 *
 * Esta clase define:
 * - Qué endpoints son públicos
 * - Qué endpoints requieren autenticación
 * - Qué endpoints están protegidos por rol (ADMIN / USER)
 * - Que la aplicación funciona sin sesiones (JWT)
 * - En qué punto se ejecuta el filtro JWT
 */
@Configuration // Indica que esta clase contiene configuración de Spring
@EnableWebSecurity // Activa la seguridad de Spring en la aplicación
@RequiredArgsConstructor // Lombok genera automáticamente el constructor con los atributos final
public class securityConfig {

    // Filtro personalizado que valida los tokens JWT en cada petición
    private final filtrarToken filtrarJwt;

    // Proveedor de autenticación que valida usuario y contraseña desde la BBDD
    private final AuthenticationProvider Authproveedor;

    /**
     * SecurityFilterChain define TODAS las reglas de acceso de la aplicación.
     * Aquí se decide quién puede acceder a cada endpoint.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                // 1. Desactivamos CSRF porque usamos JWT y no sesiones
                .csrf(csrf -> csrf.disable())

                // 2. Configuramos las reglas de autorización por endpoint
                .authorizeHttpRequests(auth -> auth

                        // ✅ SOLO el login es público
                        .requestMatchers("/acceso/login").permitAll()

                        // 🔒 SOLO el ADMIN puede registrar nuevos usuarios
                        .requestMatchers("/acceso/register").hasRole("ADMIN")

                        // 🔒 SOLO el ADMIN puede gestionar usuarios
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // ✅ Todo lo demás requiere estar autenticado (USER o ADMIN)
                        .anyRequest().authenticated()
                )

                // 3. Indicamos que la aplicación es STATELESS (sin sesiones)
                // Cada petición debe llevar su token JWT
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Indicamos qué proveedor se usa para comprobar usuario y contraseña
                .authenticationProvider(Authproveedor)

                // 5. Añadimos nuestro filtro JWT antes del filtro clásico de Spring
                .addFilterBefore(filtrarJwt, UsernamePasswordAuthenticationFilter.class)

                // 6. Devolvemos la cadena de filtros ya construida
                .build();
    }
}
