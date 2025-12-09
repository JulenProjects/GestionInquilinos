package com.example.sistemaInquilinos.seguridad.dtos;

import com.example.sistemaInquilinos.entidad.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la petición de REGISTRO de un usuario.
 * Contiene los datos necesarios para crear un nuevo usuario:
 * - Usuario
 * - Contraseña
 * - Rol
 */
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
@Builder // Permite crear el objeto usando el patrón Builder
@AllArgsConstructor // Constructor con todos los campos
@NoArgsConstructor  // Constructor vacío (necesario para JSON y Spring)
public class registerRequest {

    /**
     * Nombre de usuario del nuevo usuario a registrar.
     */
    private String usuario;

    /**
     * Contraseña del nuevo usuario.
     * Esta contraseña será cifrada con BCrypt antes de guardarse en la base de datos.
     */
    private String password;

    /**
     * Rol del usuario (ADMIN o USER).
     * Este valor determinará a qué endpoints podrá acceder.
     */
    private Role role;
}
