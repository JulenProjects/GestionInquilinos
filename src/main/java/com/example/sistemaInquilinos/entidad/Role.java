package com.example.sistemaInquilinos.entidad;

/**
 * Enum que representa los roles de los usuarios dentro del sistema.
 *
 * Se utiliza para gestionar la autorización y controlar qué acciones
 * puede realizar cada usuario según su perfil.
 */
public enum Role {

    /**
     * Rol de administrador:
     * Tiene acceso total al sistema (gestión de usuarios, inmuebles, pagos, etc.).
     */
    ADMIN,

    /**
     * Rol de usuario estándar:
     * Tiene acceso limitado a algunas funcionalidades.
     */
    USER
}
