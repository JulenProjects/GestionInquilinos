package com.example.sistemaInquilinos.servicio;

import com.example.sistemaInquilinos.entidad.inmueble;
import com.example.sistemaInquilinos.repositorio.inmuebleRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio que contiene la lógica de negocio relacionada con los Inmuebles.
 *
 * Actúa como intermediario entre el controlador y el repositorio.
 * Aquí se gestionan las operaciones principales sobre los inmuebles.
 */
@Service // Indica que esta clase es un componente de tipo servicio en Spring
@RequiredArgsConstructor // Inyección automática del repositorio mediante constructor
public class inmuebleServicio {

    /**
     * Repositorio que permite acceder a la base de datos para la entidad Inmueble.
     * Se inyecta automáticamente gracias a @RequiredArgsConstructor.
     */
    private final inmuebleRepositorio repositorio;

    /**
     * Obtiene el listado completo de inmuebles.
     *
     * @return Lista de todos los inmuebles existentes en la base de datos.
     */
    public List<inmueble> listarInmuebles() {
        return this.repositorio.findAll();
    }

    /**
     * Busca un inmueble por su ID.
     *
     * @param id ID del inmueble a buscar.
     * @return El inmueble si existe, o null si no se encuentra.
     */
    public inmueble buscarInmueblePorId(Integer id) {
        return this.repositorio.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo inmueble o actualiza uno existente.
     *
     * @param inmueble Objeto inmueble a guardar.
     * @return El inmueble persistido en la base de datos.
     */
    public inmueble guardarInmueble(inmueble inmueble) {
        return this.repositorio.save(inmueble);
    }

    /**
     * Elimina un inmueble de la base de datos por su ID.
     *
     * @param id ID del inmueble a eliminar.
     */
    public void eliminarPorId(Integer id) {
        repositorio.deleteById(id);
    }
}
