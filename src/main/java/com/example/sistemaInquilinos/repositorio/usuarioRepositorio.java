package com.example.sistemaInquilinos.repositorio;

import com.example.sistemaInquilinos.entidad.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 *
 * Se utiliza principalmente para:
 * - Autenticación (login)
 * - Registro de usuarios
 * - Gestión de usuarios desde el backend
 */
public interface usuarioRepositorio extends JpaRepository<usuario, Integer> {

    /**
     * Esto es un Query Method
     * Busca un usuario por su nombre de usuario.
     *
     * Devuelve un Optional para manejar correctamente el caso
     * en el que el usuario no exista (evita NullPointerException).
     *
     * Este método es fundamental para:
     * - El login
     * - La autenticación con Spring Security
     * - La generación del token JWT
     */
    Optional<usuario> findByUsuario(String usuario);

}
