package com.example.sistemaInquilinos.seguridad.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la petición de LOGIN.
 * Contiene únicamente los datos necesarios para iniciar sesión:
 * - Usuario
 * - Contraseña
 */
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
@Builder // Permite crear el objeto con el patrón Builder (más limpio y seguro)
@AllArgsConstructor // Constructor con todos los campos
@NoArgsConstructor  // Constructor vacío (necesario para Spring y JSON)
public class loginRequest {

    /**
     * Nombre de usuario introducido en el login.
     * No puede estar vacío.
     */
    @NotBlank(message = "El usuario no puede estar vacío")
    private String usuario;

    /**
     * Contraseña introducida en el login.
     * No puede estar vacía.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
