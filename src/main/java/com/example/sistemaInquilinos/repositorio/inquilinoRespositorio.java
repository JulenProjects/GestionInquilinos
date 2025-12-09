package com.example.sistemaInquilinos.repositorio;

import com.example.sistemaInquilinos.entidad.inquilino;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad Inquilino.
 *
 * Permite realizar todas las operaciones CRUD sobre la tabla "inquilino"
 * sin necesidad de escribir consultas SQL manualmente.
 *
 * Hereda automáticamente métodos como:
 * - findAll()           -> obtener todos los inquilinos
 * - findById(id)        -> buscar un inquilino por su ID
 * - save(inquilino)    -> guardar o actualizar un inquilino
 * - deleteById(id)     -> eliminar un inquilino por su ID
 */
public interface inquilinoRespositorio extends JpaRepository<inquilino, Integer> {

    // De momento no se necesitan métodos personalizados,
    // pero aquí podrían añadirse búsquedas específicas en el futuro.
}
