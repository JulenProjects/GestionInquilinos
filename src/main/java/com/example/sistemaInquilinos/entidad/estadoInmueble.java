package com.example.sistemaInquilinos.entidad;

/**
 * Enum que representa los posibles estados de un inmueble.
 *
 * Se utiliza para controlar de forma segura los estados permitidos
 * sin usar Strings sueltos (evita errores y mejora la validación).
 */
public enum estadoInmueble {

    // El inmueble no tiene inquilino asignado
    VACIO,

    // El inmueble está actualmente ocupado por un inquilino
    OCUPADO,

    // El inmueble tiene pagos pendientes o deuda asociada
    CON_DEUDA
}
