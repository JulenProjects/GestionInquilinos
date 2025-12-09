package com.example.sistemaInquilinos.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un Inquilino dentro del sistema.
 *
 * Se corresponde con la tabla "inquilino" en la base de datos.
 * Contiene los datos personales básicos del inquilino.
 */
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
@Entity // Indica que esta clase es una entidad JPA
@AllArgsConstructor // Constructor con todos los atributos
@NoArgsConstructor  // Constructor vacío necesario para JPA
@Table(name = "inquilino") // Nombre de la tabla en la base de datos
public class inquilino {

    /**
     * Identificador único del inquilino (Primary Key).
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inquilino")
    private Integer idInquilino;

    // Nombre completo del inquilino
    private String nombre;

    // DNI del inquilino
    private String dni;

    // Teléfono de contacto
    private String telefono;

    // Email del inquilino
    private String email;


}
