package com.example.sistemaInquilinos.servicioDTO;

import com.example.sistemaInquilinos.dto.pagosDTO;
import com.example.sistemaInquilinos.entidad.pagos;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un servicio de Spring
public class pagosDTOServicioLista {

    /**
     * Convierte una entidad Pago en un DTO.
     * Se utiliza para enviar solo los datos necesarios al frontend
     * sin exponer relaciones completas ni estructuras internas.
     */
    public pagosDTO convertirAPagosDTO(pagos pago) {

        // Creamos el objeto DTO que devolveremos al frontend
        pagosDTO dto = new pagosDTO();

        // Copiamos los datos simples del pago
        dto.setIdPago(pago.getIdPago());
        dto.setAnio(pago.getAnio());
        dto.setMes(pago.getMes());
        dto.setPrecioAlquiler(pago.getPrecioAlquiler());
        dto.setMontoDeuda(pago.getMontoDeuda());
        dto.setPagado(pago.getPagado());

        // Si el pago tiene un inquilino asociado,
        // guardamos solo su ID en el DTO
        if (pago.getInquilino() != null) {
            dto.setIdInquilino(pago.getInquilino().getIdInquilino());
        }

        // Si el pago tiene un inmueble asociado,
        // guardamos solo su ID en el DTO
        if (pago.getInmueble() != null) {
            dto.setIdInmueble(pago.getInmueble().getIdInmueble());
        }

        // Devolvemos el DTO ya preparado
        return dto;
    }
}
