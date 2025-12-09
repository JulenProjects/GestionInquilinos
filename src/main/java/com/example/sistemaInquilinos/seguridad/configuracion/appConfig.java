package com.example.sistemaInquilinos.seguridad.configuracion;

import com.example.sistemaInquilinos.repositorio.usuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración principal de seguridad para la AUTENTICACIÓN.
 * Aquí se definen los beans que Spring Security necesita para:
 * - Buscar usuarios en la base de datos
 * - Comparar contraseñas
 * - Gestionar el proceso de login
 */
@Configuration // Indica que esta clase contiene configuración de Spring
@RequiredArgsConstructor // Lombok genera automáticamente un constructor con los atributos final
public class appConfig {

    // Repositorio para acceder a los usuarios en la base de datos
    private final usuarioRepositorio usuarioRepositorio;

    /**
     * AuthenticationManager es el motor principal de autenticación.
     * Se utiliza cuando se ejecuta authenticationManager.authenticate(...)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * AuthenticationProvider es quien define:
     * - De dónde se sacan los usuarios (BBDD)
     * - Con qué algoritmo se comparan las contraseñas (BCrypt)
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider ourProvider = new DaoAuthenticationProvider();

        // Le decimos cómo cargar usuarios desde la base de datos
        ourProvider.setUserDetailsService(userDetailsService());

        // Le indicamos que las contraseñas están cifradas con BCrypt
        ourProvider.setPasswordEncoder(passwordEncoder());

        return ourProvider;
    }

    /**
     * Bean que se encarga de cifrar contraseñas.
     * Se usa en el registro y en el login de usuarios.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService es el método que Spring Security usa para:
     * - Buscar un usuario por su nombre
     * - Cargarlo como UserDetails
     * - Lanzar error si no existe
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepositorio.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

}
