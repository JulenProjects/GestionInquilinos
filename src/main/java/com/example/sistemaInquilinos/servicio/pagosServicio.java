package com.example.sistemaInquilinos.servicio;

import com.example.sistemaInquilinos.entidad.estadoInmueble;
import com.example.sistemaInquilinos.entidad.pagos;
import com.example.sistemaInquilinos.repositorio.pagosRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Servicio encargado de gestionar toda la lógica relacionada con los pagos.
 *
 * Aquí se controla:
 * - Listado de pagos
 * - Búsqueda por ID
 * - Eliminación
 * - Cálculo de deudas
 * - Filtros por inmueble, impagos y fecha
 */
@Service // Le indica a Spring que esta clase es un servicio
@RequiredArgsConstructor
// Genera automáticamente un constructor con los atributos final
// Gracias a esto, Spring inyecta el repositorio sin usar @Autowired
public class pagosServicio {

    /**
     * Repositorio que permite acceder a los pagos en la base de datos.
     */
    private final pagosRepositorio pagosRepositorio;

    // =========================
    // LISTADOS Y BÚSQUEDAS
    // =========================

    /**
     * Devuelve el listado completo de todos los pagos.
     */
    public List<pagos> listarPagos() {
        return pagosRepositorio.findAll();
    }

    /**
     * Busca un pago por su ID.
     * Si no existe, devuelve null.
     */
    public pagos buscarPagoPorId(Integer idPago) {
        return pagosRepositorio.findById(idPago).orElse(null);
    }

    // =========================
    // ELIMINACIÓN
    // =========================

    /**
     * Elimina un pago de la base de datos usando su ID.
     */
    public void eliminarPagoPorId(Integer idPago) {
        pagosRepositorio.deleteById(idPago);
    }

    // =========================
    // CÁLCULO DE DEUDA
    // =========================

    /**
     * Calcula la deuda total de un inmueble sumando todos los pagos no pagados.
     */
    public Double calcularDeudaTotalPorInmueble(Integer idInmueble) {

        // Obtiene todos los pagos de ese inmueble
        List<pagos> lista = pagosRepositorio.findByInmuebleIdInmueble(idInmueble);

        // Suma solo los pagos marcados como no pagados
        double deuda = 0.0;

        for (pagos p : lista) {
            // Solo sumamos deuda si:
            // - El pago NO está pagado
            // - El inmueble NO está vacío
            if (!p.getPagado() && p.getInmueble().getEstado() != estadoInmueble.VACIO) {
                deuda += p.getPrecioAlquiler();
            }
        }


        return deuda;
    }

    // =========================
    // GUARDAR / ACTUALIZAR PAGO
    // =========================

    /**
     * Guarda un pago y actualiza automáticamente el monto de deuda del inmueble.
     */
    public pagos guardarPago(pagos pago) {

        // Aseguramos que el precio del alquiler del mes es el del inmueble actual
        pago.setPrecioAlquiler(pago.getInmueble().getPrecioMensual());

        pagos guardado = pagosRepositorio.save(pago);

        Integer idInmueble = guardado.getInmueble().getIdInmueble();
        Double deudaActual = calcularDeudaTotalPorInmueble(idInmueble);

        guardado.setMontoDeuda(deudaActual);

        return pagosRepositorio.save(guardado);
    }


    // =========================
    // FILTROS AVANZADOS
    // =========================

    /**
     * Devuelve todos los pagos asociados a un inmueble.
     */
    public List<pagos> listarPagosPorInmueble(Integer idInmueble) {
        return pagosRepositorio.findByInmuebleIdInmueble(idInmueble);
    }

    /**
     * Devuelve todos los pagos que están marcados como impagos.
     */
    public List<pagos> listarImpagos() {
        return pagosRepositorio.findByPagadoFalse();
    }

    /**
     * Busca pagos filtrando por año y mes.
     * Incluye validación para evitar valores incorrectos.
     */
    public List<pagos> buscarPagosPorAnioYMes(Integer anio, Integer mes) {

        // Validación básica para evitar errores
        if (anio == null || mes == null) {
            return null;
        }

        if (mes < 1 || mes > 12) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El mes debe estar entre 1 y 12"
            );
        }

        return pagosRepositorio.findByAnioAndMes(anio, mes);
    }
}



