package com.example.sistemaInquilinos.seguridad.servicio;

import com.example.sistemaInquilinos.entidad.Role;
import com.example.sistemaInquilinos.entidad.usuario;
import com.example.sistemaInquilinos.repositorio.usuarioRepositorio;
import com.example.sistemaInquilinos.seguridad.creacionYValidacionToken.jwtService;
import com.example.sistemaInquilinos.seguridad.dtos.loginRequest;
import com.example.sistemaInquilinos.seguridad.dtos.registerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio principal de SEGURIDAD.
 * Aquí se gestiona:
 * - El LOGIN de usuarios
 * - El REGISTRO de nuevos usuarios
 * - La generación del TOKEN JWT
 */
@Service // Indica que esta clase es un servicio de Spring
@RequiredArgsConstructor // Lombok genera automáticamente el constructor con los atributos final
public class seguridadServicio {

    // Motor principal de autenticación de Spring Security
    private final AuthenticationManager authenticationManager;

    // Repositorio para acceder a los usuarios en la base de datos
    private final usuarioRepositorio usuarioRepositorio;

    // Servicio encargado de crear y validar los tokens JWT
    private final jwtService jwtService;

    // Codificador de contraseñas (BCrypt)
    private final PasswordEncoder passwordEncoder;

    /**
     * LOGIN DE USUARIO
     * 1. Comprueba si usuario y contraseña son correctos
     * 2. Si son correctos, genera un token JWT
     * 3. Devuelve el token al usuario
     */
    public tokenDevolver login(loginRequest request) {

        // 1. Autenticamos las credenciales con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsuario(),
                        request.getPassword()
                )
        );

        // 2. Si no ha habido excepción, buscamos el usuario en la base de datos
        UserDetails user = usuarioRepositorio
                .findByUsuario(request.getUsuario())
                .orElseThrow();

        // 3. Generamos el token JWT para ese usuario
        String tokenUsuario = jwtService.getTokenFinal(user);

        // 4. Devolvemos el token al cliente
        return tokenDevolver.builder()
                .token(tokenUsuario)
                .build();
    }

    /**
     * REGISTRO DE USUARIO
     * 1. Se crea un nuevo usuario
     * 2. Se cifra la contraseña
     * 3. Se guarda en la base de datos
     * 4. Se genera un token JWT automáticamente
     * 5. Se devuelve el token
     */
    public tokenDevolver register(registerRequest request) {

        // 1. Creamos el nuevo usuario usando Builder
        usuario user = usuario.builder()
                .usuario(request.getUsuario())
                // La contraseña se guarda cifrada
                .password(passwordEncoder.encode(request.getPassword()))
                // El rol se recibe desde el request
                .role(request.getRole())
                .build();

        // 2. Guardamos el usuario en la base de datos
        usuarioRepositorio.save(user);

        // 3. Generamos el token JWT para el usuario recién registrado
        String tokenUsuarioRegistrado = jwtService.getTokenFinal(user);

        // 4. Devolvemos el token al cliente
        return tokenDevolver.builder()
                .token(tokenUsuarioRegistrado)
                .build();
    }

}
