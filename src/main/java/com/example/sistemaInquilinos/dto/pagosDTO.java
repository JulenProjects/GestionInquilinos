package com.example.sistemaInquilinos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para representar un pago.
 *
 * Se utiliza para enviar información de los pagos al frontend sin exponer
 * directamente la entidad completa ni las relaciones complejas.
 */
@Data
// Genera getters, setters, equals, hashCode y toString automáticamente

@Builder
// Permite crear instancias de forma clara, legible y segura usando el patrón Builder
// Ejemplo:
// pagosDTO pago = pagosDTO.builder()
//      .anio(2025)
//      .mes(3)
//      .monto(750.0)
//      .pagado(false)
//      .idInquilino(2)
//      .idInmueble(5)
//      .build();

@AllArgsConstructor
// Constructor con todos los atributos

@NoArgsConstructor
// Constructor vacío necesario para serialización/deserialización JSON
public class pagosDTO {

    // ID único del pago
    private Integer idPago;

    // Año al que pertenece el pago
    private Integer anio;

    // Mes del pago (1–12)
    private Integer mes;

    // Importe pagado
    private Double precioAlquiler;

    // Importe pendiente de deuda, si existe
    private Double montoDeuda;

    // Indica si el pago está realizado o no
    private Boolean pagado;

    // ID del inquilino asociado al pago (solo el ID, no el objeto completo)
    private Integer idInquilino;

    // ID del inmueble asociado al pago (solo el ID, no el objeto completo)
    private Integer idInmueble;
}
