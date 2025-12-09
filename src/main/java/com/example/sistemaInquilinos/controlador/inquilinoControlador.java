package com.example.sistemaInquilinos.controlador;

import com.example.sistemaInquilinos.entidad.inquilino;
import com.example.sistemaInquilinos.servicio.inquilinoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST encargado de gestionar todas las operaciones
 * relacionadas con los inquilinos de la aplicación.
 *
 * Aquí se reciben las peticiones HTTP y se delega la lógica de negocio
 * al servicio correspondiente.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/inquilinos") // Ruta base del controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde Angular en este caso
public class inquilinoControlador {

    // Logger para mostrar información en la consola
    private static final Logger logger = LoggerFactory.getLogger(inquilinoControlador.class);

    // Servicio que contiene la lógica de negocio de los inquilinos
    @Autowired
    private inquilinoServicio inquilinoServicio;

    /**
     * Obtiene el listado completo de inquilinos.
     *
     * URL: GET /inquilinos/listado
     */
    @GetMapping("/listado")
    public List<inquilino> obtenerInquilinos() {

        // Se obtienen todos los inquilinos desde la base de datos
        List<inquilino> inquilinos = this.inquilinoServicio.listaInquilinos();

        // Se muestran en consola para control
        logger.info("Listado de inquilinos: ");
        inquilinos.forEach(inquilino1x1 -> logger.info(inquilino1x1.toString()));

        return inquilinos;
    }

    /**
     * Crea un nuevo inquilino en la base de datos.
     *
     * URL: POST /inquilinos/listado
     */
    @PostMapping("/listado")
    public inquilino agregarInquilino(@RequestBody inquilino inquilinoAñadido) {

        logger.info("Inquilino a agregar: " + inquilinoAñadido);

        // Se guarda el inquilino en la base de datos
        return this.inquilinoServicio.guardarInquilino(inquilinoAñadido);
    }

    /**
     * Obtiene un inquilino por su ID.
     *
     * URL: GET /inquilinos/listado/{idInquilino}
     */
    @GetMapping("/listado/{idInquilino}")
    public ResponseEntity<inquilino> obtenerInquilinoPorId(@PathVariable int idInquilino) {

        // Se busca el inquilino por ID
        inquilino inquilino = this.inquilinoServicio.buscarInquilinoPorId(idInquilino);

        if (inquilino != null) {
            logger.info("Inquilino encontrado: " + inquilino);
            return ResponseEntity.ok(inquilino);
        } else {
            logger.info("Inquilino no encontrado, id buscado: " + inquilino);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un inquilino existente.
     *
     * URL: PUT /inquilinos/listado/{idInquilino}
     */
    @PutMapping("/listado/{idInquilino}")
    public ResponseEntity<inquilino> actualizarInquilino(
            @PathVariable int idInquilino,
            @RequestBody inquilino inquilinoRecibido) {

        // Se busca el inquilino en la base de datos
        inquilino inquilino = this.inquilinoServicio.buscarInquilinoPorId(idInquilino);

        // Se actualizan los datos del inquilino
        inquilino.setNombre(inquilinoRecibido.getNombre());
        inquilino.setDni(inquilinoRecibido.getDni());
        inquilino.setTelefono(inquilinoRecibido.getTelefono());
        inquilino.setEmail(inquilinoRecibido.getEmail());

        // Se guarda la información actualizada
        this.inquilinoServicio.guardarInquilino(inquilino);

        return ResponseEntity.ok(inquilino);
    }

    /**
     * Elimina un inquilino por su ID.
     *
     * URL: DELETE /inquilinos/listado/{idInquilino}
     */
    @DeleteMapping("/listado/{idInquilino}")
    public ResponseEntity<Map<String, Boolean>> eliminarInquilino(@PathVariable int idInquilino) {

        // Se busca el inquilino a eliminar
        inquilino inquilino = this.inquilinoServicio.buscarInquilinoPorId(idInquilino);

        if (inquilino == null) {
            logger.info("Inquilino no encontrado, id buscado: " + inquilino);
            return ResponseEntity.notFound().build();
        } else {

            // Se elimina de la base de datos
            this.inquilinoServicio.eliminarPorId(inquilino.getIdInquilino());

            // Se devuelve una respuesta confirmando la eliminación
            Map<String, Boolean> respuesta = new HashMap<>();
            respuesta.put("eliminado", Boolean.TRUE);

            return ResponseEntity.ok(respuesta);
        }
    }
}
















