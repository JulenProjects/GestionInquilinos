package com.example.sistemaInquilinos.servicioDTO;

import com.example.sistemaInquilinos.dto.inmuebleDTO;
import com.example.sistemaInquilinos.entidad.inmueble;
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un servicio gestionado por Spring
public class inmuebleDTOServicioLista {

    /**
     * Convierte una entidad Inmueble en su versión DTO.
     * Se utiliza para enviar solo los datos necesarios al frontend
     * sin exponer toda la estructura interna de la base de datos.
     */
    public inmuebleDTO inmuebleDTOServicio(inmueble inmueble) {

        // Creamos el objeto DTO que devolveremos
        inmuebleDTO dto = new inmuebleDTO();

        // Copiamos los datos simples del inmueble al DTO
        dto.setIdInmueble(inmueble.getIdInmueble());
        dto.setDireccion(inmueble.getDireccion());
        dto.setCiudad(inmueble.getCiudad());
        dto.setCodigoPostal(inmueble.getCodigoPostal());
        dto.setPrecioMensual(inmueble.getPrecioMensual());

        // Convertimos el enum a String para facilitar su uso en el frontend
        dto.setEstado(inmueble.getEstado().name());

        // Si el inmueble tiene un inquilino asignado,
        // guardamos solo su ID en el DTO
        if (inmueble.getInquilino() != null) {
            dto.setIdInquilino(inmueble.getInquilino().getIdInquilino());
        }

        // Devolvemos el DTO ya preparado
        return dto;
    }
}
