package com.example.sistemaInquilinos.seguridad.servicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * clase que se utiliza para DEVOLVER el token JWT al usuario
 * después de hacer login o register correctamente.
 *
 * Este objeto es la respuesta que recibe el frontend.
 */
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
@Builder // Permite construir el objeto usando el patrón Builder (más limpio y seguro)
@NoArgsConstructor // Constructor vacío (necesario para JSON, Spring, etc.)
@AllArgsConstructor // Constructor con todos los campos
public class tokenDevolver {

    /**
     * Token JWT generado tras una autenticación correcta.
     * Este token es el que se usará en las peticiones protegidas.
     */
    String token;
}
