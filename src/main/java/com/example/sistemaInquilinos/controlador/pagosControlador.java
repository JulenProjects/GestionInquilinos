package com.example.sistemaInquilinos.controlador;

import com.example.sistemaInquilinos.dto.pagosDTO;
import com.example.sistemaInquilinos.entidad.pagos;
import com.example.sistemaInquilinos.servicio.pagosServicio;
import com.example.sistemaInquilinos.servicioDTO.pagosDTOServicioLista;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST encargado de gestionar los pagos del sistema.
 *
 * Se encarga de:
 * - Listar pagos
 * - Crear pagos
 * - Actualizar pagos
 * - Eliminar pagos
 * - Buscar pagos por inmueble
 * - Buscar pagos por fecha
 * - Listar impagos
 */
@RestController
@RequestMapping("/pagos")
public class pagosControlador {

    // Logger para mostrar información del flujo del backend
    private static final Logger logger = LoggerFactory.getLogger(pagosControlador.class);

    // Servicio que contiene la lógica de negocio de pagos
    @Autowired
    private pagosServicio pagosServicio;

    // Servicio encargado de convertir entidad pagos a pagosDTO
    @Autowired
    private pagosDTOServicioLista pagosDTOServicioLista;

    /**
     * Obtiene el listado completo de pagos en formato DTO.
     *
     * URL: GET /pagos/listado
     */
    @GetMapping("/listado")
    public List<pagosDTO> obtenerListaPagos() {

        // Se obtiene la lista completa de pagos desde la base de datos
        List<pagos> pagos = pagosServicio.listarPagos();

        // Se convierte la lista a DTOs
        List<pagosDTO> respuesta = new ArrayList<>();

        for (pagos todos : pagos) {
            pagosDTO dto = pagosDTOServicioLista.convertirAPagosDTO(todos);
            respuesta.add(dto);
        }

        logger.info("Listado de pagos DTO:");
        respuesta.forEach(reducido -> logger.info(reducido.toString()));

        return respuesta;
    }

    /**
     * Obtiene un pago por su ID.
     *
     * URL: GET /pagos/listado/{idPago}
     */
    @GetMapping("/listado/{idPago}")
    public ResponseEntity<pagosDTO> obtenerPagoPorId(@PathVariable Integer idPago) {

        pagos pago = pagosServicio.buscarPagoPorId(idPago);

        if (pago == null) {
            logger.warn("Pago NO encontrado con ID: " + idPago);
            return ResponseEntity.notFound().build();
        }

        pagosDTO dto = pagosDTOServicioLista.convertirAPagosDTO(pago);
        logger.info("Pago encontrado DTO: " + dto);

        return ResponseEntity.ok(dto);
    }

    /**
     * Crea un nuevo pago en la base de datos.
     *
     * URL: POST /pagos/listado/nuevo
     */
    @PostMapping("/listado/nuevo")
    public ResponseEntity<pagos> crearPago(@Valid @RequestBody pagos pagoRecibido) {

        logger.info("Pago recibido para crear: " + pagoRecibido);

        pagos guardado = pagosServicio.guardarPago(pagoRecibido);

        return ResponseEntity.ok(guardado);
    }

    /**
     * Actualiza un pago existente.
     *
     * URL: PUT /pagos/listado/{idPago}
     */
    @PutMapping("/listado/{idPago}")
    public ResponseEntity<pagos> actualizarPago(
            @PathVariable Integer idPago,
            @Valid @RequestBody pagos pagoRecibido) {

        pagos pagoBD = pagosServicio.buscarPagoPorId(idPago);

        if (pagoBD == null) {
            logger.warn("Pago NO encontrado para actualizar: " + idPago);
            return ResponseEntity.notFound().build();
        }

        // Se actualizan los datos del pago
        pagoBD.setAnio(pagoRecibido.getAnio());
        pagoBD.setMes(pagoRecibido.getMes());
        pagoBD.setMontoDeuda(pagoRecibido.getMontoDeuda());
        pagoBD.setPagado(pagoRecibido.getPagado());
        pagoBD.setInquilino(pagoRecibido.getInquilino());
        pagoBD.setInmueble(pagoRecibido.getInmueble());

        pagos actualizado = pagosServicio.guardarPago(pagoBD);

        logger.info("Pago actualizado: " + actualizado);

        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un pago por su ID.
     *
     * URL: DELETE /pagos/listado/{idPago}
     */
    @DeleteMapping("/listado/{idPago}")
    public ResponseEntity<?> eliminarPago(@PathVariable Integer idPago) {

        pagos pago = pagosServicio.buscarPagoPorId(idPago);

        if (pago == null) {
            logger.warn("Pago NO encontrado para eliminar: " + idPago);
            return ResponseEntity.notFound().build();
        }

        pagosServicio.eliminarPagoPorId(idPago);

        logger.info("Pago eliminado con ID: " + idPago);

        return ResponseEntity.ok("Pago eliminado correctamente");
    }

    /**
     * Obtiene el histórico de pagos de un inmueble concreto.
     *
     * URL: GET /pagos/listado/porInmueble/{idInmueble}
     */
    @GetMapping("/listado/porInmueble/{idInmueble}")
    public ResponseEntity<List<pagosDTO>> obtenerPagosPorInmueble(@PathVariable Integer idInmueble) {

        List<pagos> lista = pagosServicio.listarPagosPorInmueble(idInmueble);

        if (lista == null) {
            logger.warn("No hay pagos para el inmueble con ID: " + idInmueble);
            return ResponseEntity.ok(new ArrayList<>());
        }

        // Conversión a DTO
        List<pagosDTO> respuesta = new ArrayList<>();
        for (pagos historico : lista) {
            respuesta.add(pagosDTOServicioLista.convertirAPagosDTO(historico));
        }

        logger.info("Histórico de pagos DTO para inmueble " + idInmueble);

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Obtiene el listado completo de pagos impagados.
     *
     * URL: GET /pagos/listado/impagos
     */
    @GetMapping("/listado/impagos")
    public ResponseEntity<List<pagosDTO>> obtenerImpagos() {

        List<pagos> lista = pagosServicio.listarImpagos();
        List<pagosDTO> respuesta = new ArrayList<>();

        for (pagos p : lista) {
            respuesta.add(pagosDTOServicioLista.convertirAPagosDTO(p));
        }

        logger.info("Listado de impagos devuelto");

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Obtiene pagos filtrados por año y mes.
     *
     * URL: GET /pagos/listado/porFecha/{anio}/{mes}
     */
    @GetMapping("/listado/porFecha/{anio}/{mes}")
    public ResponseEntity<List<pagosDTO>> obtenerPagosPorFecha(
            @PathVariable Integer anio,
            @PathVariable Integer mes) {

        List<pagos> lista = pagosServicio.buscarPagosPorAnioYMes(anio, mes);

        List<pagosDTO> dto = new ArrayList<>();
        for (pagos conversion : lista) {
            pagosDTO convertido = pagosDTOServicioLista.convertirAPagosDTO(conversion);
            dto.add(convertido);
        }

        return ResponseEntity.ok(dto);
    }
}
