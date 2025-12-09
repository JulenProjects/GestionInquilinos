package com.example.sistemaInquilinos.repositorio;

import com.example.sistemaInquilinos.entidad.pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Pagos.
 *
 * Permite realizar operaciones CRUD sobre la tabla "pagos"
 * y además incluye consultas personalizadas creadas
 * automáticamente por Spring Data JPA.
 */
public interface pagosRepositorio extends JpaRepository<pagos, Integer> {

    /**
     * Obtiene todos los pagos asociados a un inmueble concreto
     * a partir del ID del inmueble.
     *
     * Spring interpreta:
     * findByInmuebleIdInmueble → pagos.getInmueble().getIdInmueble()
     */
    List<pagos> findByInmuebleIdInmueble(Integer idInmueble);

    /**
     * Obtiene todos los pagos que están marcados como NO pagados.
     *
     * Equivale a: SELECT * FROM pagos WHERE pagado = false
     */
    List<pagos> findByPagadoFalse();

    /**
     * Obtiene pagos filtrando por año y mes.
     *
     * Equivale a:
     * SELECT * FROM pagos WHERE anio = ? AND mes = ?
     */
    List<pagos> findByAnioAndMes(Integer anio, Integer mes);
}
