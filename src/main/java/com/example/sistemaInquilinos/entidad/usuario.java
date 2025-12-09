package com.example.sistemaInquilinos.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un Usuario del sistema.
 *
 * Esta clase también implementa la interfaz UserDetails de Spring Security,
 * lo que permite que Spring la use directamente para la autenticación
 * y autorización mediante JWT.
 */
@Entity // Indica que es una entidad JPA
@Table(name = "usuario") // Nombre de la tabla en la base de datos
@Data // Genera getters, setters, toString, equals y hashCode
@AllArgsConstructor // Constructor con todos los campos
@NoArgsConstructor  // Constructor vacío necesario para JPA
@Builder // Permite crear usuarios usando el patrón Builder (muy útil en register)
public class usuario implements UserDetails {

    /**
     * ID único del usuario (Primary Key).
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * Nombre de usuario para el login.
     */
    private String usuario;

    /**
     * Contraseña del usuario (se almacena encriptada con BCrypt).
     */
    private String password;

    /**
     * Rol del usuario dentro del sistema (ADMIN o USER).
     * Se guarda como texto gracias a EnumType.STRING.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Devuelve los permisos del usuario a Spring Security.
     *
     * A partir del rol (ADMIN o USER), se crea una autorización
     * que Spring utilizará para proteger los endpoints.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }


    /**
     * Devuelve el nombre de usuario que se utilizará en el login.
     */
    @Override
    public String getUsername() {
        return usuario;
    }

    /**
     * Devuelve la contraseña encriptada del usuario.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Indica si la cuenta ha expirado.
     * En este proyecto siempre devuelve true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta está bloqueada.
     * En este proyecto siempre devuelve true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales han expirado.
     * En este proyecto siempre devuelve true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     * En este proyecto siempre devuelve true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
