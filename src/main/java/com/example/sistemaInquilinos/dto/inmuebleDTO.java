package com.example.sistemaInquilinos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para enviar información del inmueble
 * al frontend de forma segura, sin exponer la entidad completa.
 *
 * También se usa para mostrar solo los datos necesarios en listados.
 */
@Data
// Genera automáticamente getters, setters, equals, hashCode y toString

@Builder
// Permite crear objetos de forma más clara y ordenada, por ejemplo:
// inmuebleDTO dto = inmuebleDTO.builder()
//      .idInmueble(1)
//      .direccion("Calle Mayor 10")
//      .ciudad("Barcelona")
//      .precioMensual(900.0)
//      .build();
// Evita constructores largos y mejora la legibilidad del código

@AllArgsConstructor
// Crea un constructor con todos los atributos del DTO

@NoArgsConstructor
// Crea un constructor vacío (necesario para frameworks como Spring o Jackson)
public class inmuebleDTO {

    // ID único del inmueble
    private Integer idInmueble;

    // Dirección del inmueble
    private String direccion;

    // Ciudad donde se encuentra el inmueble
    private String ciudad;

    // Código postal del inmueble
    private String codigoPostal;

    // Precio mensual del alquiler
    private Double precioMensual;

    // Estado del inmueble (DISPONIBLE, OCUPADO, etc.)
    private String estado;

    // ID del inquilino asociado (no se envía el objeto completo)
    private Integer idInquilino;
}
