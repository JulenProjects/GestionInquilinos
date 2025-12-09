package com.example.sistemaInquilinos.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad que representa un Inmueble dentro del sistema.
 *
 * Se corresponde con la tabla "inmueble" en la base de datos.
 * Contiene la información básica del inmueble, su estado,
 * el inquilino asociado y su historial de pagos.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "inmueble") // Nombre de la tabla en la base de datos
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
@AllArgsConstructor // Constructor con todos los campos
@NoArgsConstructor // Constructor vacío (necesario para JPA)
public class inmueble {

    /**
     * Identificador único del inmueble (Primary Key).
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inmueble")
    private Integer idInmueble;

    // Dirección del inmueble
    private String direccion;

    // Ciudad donde se encuentra el inmueble
    private String ciudad;

    // Código postal del inmueble
    @Column(name = "codigo_postal")
    private String codigoPostal;

    // Precio mensual del alquiler
    @Column(name = "precio_mensual")
    private Double precioMensual;

    /**
     * Estado actual del inmueble (VACIO, OCUPADO, CON_DEUDA).
     * Se guarda como texto en la base de datos gracias a EnumType.STRING.
     */
    @Enumerated(EnumType.STRING)
    private estadoInmueble estado;

    /**
     * Relación OneToOne entre inmueble e inquilino.
     * Un inmueble puede tener como máximo un inquilino.
     *
     * fetch = FetchType.LAZY:
     * Evita que el inquilino se cargue automáticamente con el inmueble,
     * mejorando el rendimiento y evitando problemas de recursividad infinita.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_inquilino", // Columna FK en la tabla inmueble
            referencedColumnName = "id_inquilino" // Columna PK en la tabla inquilino
    )
    private inquilino inquilino;

    /**
     * Relación OneToMany:
     * Un inmueble puede tener muchos pagos asociados.
     *
     * mappedBy = "inmueble":
     * Indica que la relación está controlada desde la entidad "pagos".
     */
    @OneToMany(mappedBy = "inmueble")
    private List<pagos> pagos;
}
