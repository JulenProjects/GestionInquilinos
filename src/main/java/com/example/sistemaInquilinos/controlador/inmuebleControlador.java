package com.example.sistemaInquilinos.controlador;

import com.example.sistemaInquilinos.dto.inmuebleDTO;
import com.example.sistemaInquilinos.servicioDTO.inmuebleDTOServicioLista;
import com.example.sistemaInquilinos.entidad.inmueble;
import com.example.sistemaInquilinos.servicio.inmuebleServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST encargado de gestionar todas las operaciones
 * relacionadas con los inmuebles de la aplicación.
 *
 * Aquí se reciben las peticiones HTTP y se delega la lógica
 * al servicio correspondiente.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/inmuebles") // Ruta base de este controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde el cliente, en este caso Angular
public class inmuebleControlador {

    // Logger para mostrar mensajes en consola
    private static final Logger logger = LoggerFactory.getLogger(inmuebleControlador.class);

    // Servicio que contiene la lógica de negocio de los inmuebles
    @Autowired
    private inmuebleServicio inmuebleServicio;

    // Servicio que convierte entidades Inmueble a InmuebleDTO
    @Autowired
    private inmuebleDTOServicioLista inmuebleDTOServicioLista;

    /**
     * Obtiene el listado de todos los inmuebles en formato DTO.
     * Se usa DTO para no exponer directamente la entidad completa.
     *
     * URL: GET /inmuebles/listado
     */
    @GetMapping("/listado")
    public List<inmuebleDTO> obtenerInmuebles() {

        // Se obtienen todos los inmuebles desde la base de datos
        List<inmueble> inmuebles = inmuebleServicio.listarInmuebles();

        // Lista donde se guardarán los DTO
        List<inmuebleDTO> respuesta = new ArrayList<>();

        // Conversión de cada entidad Inmueble a su DTO correspondiente
        for (inmueble inm : inmuebles) {
            inmuebleDTO dto = inmuebleDTOServicioLista.inmuebleDTOServicio(inm);
            respuesta.add(dto);
        }

        // Se imprime el listado de inmuebles en formato DTO en consola
        logger.info("Listado de inmuebles DTO:");
        respuesta.forEach(reducido -> logger.info(reducido.toString()));

        return respuesta;
    }

    /**
     * Crea un nuevo inmueble en la base de datos.
     *
     * URL: POST /inmuebles/listado
     */
    @PostMapping("/listado")
    public inmueble agregarInmueble(@RequestBody inmueble inmuebleRecibido) {
        logger.info("Inmueble a agregar: " + inmuebleRecibido);

        // Se guarda el nuevo inmueble en la base de datos
        return this.inmuebleServicio.guardarInmueble(inmuebleRecibido);
    }

    /**
     * Obtiene un inmueble por su ID.
     *
     * URL: GET /inmuebles/listado/{idInmueble}
     */
    @GetMapping("/listado/{idInmueble}")
    public ResponseEntity<inmueble> obtenerInmueblePorId(@PathVariable int idInmueble) {

        // Se busca el inmueble por ID
        inmueble inmueble = this.inmuebleServicio.buscarInmueblePorId(idInmueble);

        if (inmueble != null) {
            logger.info("Inmueble encontrado: " + inmueble);
            return ResponseEntity.ok(inmueble);
        } else {
            logger.info("Inmueble no encontrado, id buscado: " + idInmueble);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un inmueble existente.
     *
     * URL: PUT /inmuebles/listado/{idInmueble}
     */
    @PutMapping("/listado/{idInmueble}")
    public ResponseEntity<inmueble> actualizarInmueble(
            @PathVariable int idInmueble,
            @RequestBody inmueble inmuebleRecibido) {

        // Se busca el inmueble en la base de datos
        inmueble inmueble = this.inmuebleServicio.buscarInmueblePorId(idInmueble);

        if (inmueble == null) {
            logger.info("No existe el inmueble con id: " + idInmueble);
            return ResponseEntity.notFound().build();
        }

        // Se actualizan los datos del inmueble
        inmueble.setDireccion(inmuebleRecibido.getDireccion());
        inmueble.setCiudad(inmuebleRecibido.getCiudad());
        inmueble.setCodigoPostal(inmuebleRecibido.getCodigoPostal());
        inmueble.setPrecioMensual(inmuebleRecibido.getPrecioMensual());
        inmueble.setEstado(inmuebleRecibido.getEstado());
        inmueble.setInquilino(inmuebleRecibido.getInquilino());

        // Se guarda el inmueble actualizado
        this.inmuebleServicio.guardarInmueble(inmueble);

        return ResponseEntity.ok(inmueble);
    }

    /**
     * Elimina un inmueble por su ID.
     *
     * URL: DELETE /inmuebles/listado/{idInmueble}
     */
    @DeleteMapping("/listado/{idInmueble}")
    public ResponseEntity<Map<String, Boolean>> eliminarInmueble(@PathVariable int idInmueble) {

        // Se busca el inmueble a eliminar
        inmueble inmueble = this.inmuebleServicio.buscarInmueblePorId(idInmueble);

        if (inmueble == null) {
            logger.info("Inmueble no encontrado, id buscado: " + idInmueble);
            return ResponseEntity.notFound().build();
        } else {

            // Se elimina de la base de datos
            this.inmuebleServicio.eliminarPorId(inmueble.getIdInmueble());

            // Se devuelve una respuesta indicando que fue eliminado correctamente
            Map<String, Boolean> respuesta = new HashMap<>();
            respuesta.put("eliminado", Boolean.TRUE);

            return ResponseEntity.ok(respuesta);
        }
    }
}
