package com.example.sistemaInquilinos.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un Pago dentro del sistema.
 *
 * Se corresponde con la tabla "pagos" en la base de datos.
 * Cada pago está asociado a un inquilino y a un inmueble.
 */
@Entity // Indica que es una entidad JPA
@Table(name = "pagos") // Nombre de la tabla en la base de datos
@Data // Genera getters, setters, toString, equals y hashCode
@AllArgsConstructor // Constructor con todos los campos
@NoArgsConstructor // Constructor vacío necesario para JPA
public class pagos {

    /**
     * Identificador único del pago (Primary Key).
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    /**
     * Año al que corresponde el pago.
     */
    @Column(name = "anio")
    private Integer anio;

    /**
     * Mes del pago (valores entre 1 y 12).
     * Se valida automáticamente con anotaciones.
     */
    @Min(value = 1, message = "El mes debe ser mínimo 1")
    @Max(value = 12, message = "El mes debe ser máximo 12")
    @Column(nullable = false)
    private Integer mes;   // 1–12

    /**
     * Importe total del pago realizado.
     */
    @Column(name = "precioAlquiler")
    private Double precioAlquiler;


    /**
     * Importe pendiente si existe deuda.
     */
    @Column(name = "monto_deuda")
    private Double montoDeuda;

    /**
     * Indica si el pago está realizado o no.
     * En MySQL se guarda como TINYINT(1).
     */
    @Column(columnDefinition = "BOOLEAN")
    private Boolean pagado;


    /**
     * Relación ManyToOne:
     * Muchos pagos pueden pertenecer a un mismo inquilino.
     */
    @ManyToOne
    @JoinColumn(
            name = "id_inquilino", // FK en tabla pagos
            referencedColumnName = "id_inquilino" // PK en tabla inquilino
    )
    private inquilino inquilino;

    /**
     * Relación ManyToOne:
     * Muchos pagos pueden pertenecer a un mismo inmueble.
     */
    @ManyToOne
    @JoinColumn(
            name = "id_inmueble", // FK en tabla pagos
            referencedColumnName = "id_inmueble" // PK en tabla inmueble
    )
    private inmueble inmueble;
}
