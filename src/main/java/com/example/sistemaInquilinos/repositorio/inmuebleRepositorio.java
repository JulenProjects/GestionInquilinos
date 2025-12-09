package com.example.sistemaInquilinos.repositorio;

import com.example.sistemaInquilinos.entidad.inmueble;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad Inmueble.
 *
 * Esta interfaz permite realizar operaciones CRUD automáticamente
 * sobre la tabla "inmueble" sin necesidad de escribir SQL manual.
 *
 * Gracias a JpaRepository, hereda métodos como:
 * - findAll()        -> obtener todos los inmuebles
 * - findById(id)     -> buscar un inmueble por ID
 * - save(inmueble)  -> guardar o actualizar un inmueble
 * - deleteById(id)  -> eliminar un inmueble por ID
 */
public interface inmuebleRepositorio extends JpaRepository<inmueble, Integer> {
    // No es necesario añadir métodos personalizados de momento,
    // ya que JpaRepository cubre todas las operaciones básicas.
}
