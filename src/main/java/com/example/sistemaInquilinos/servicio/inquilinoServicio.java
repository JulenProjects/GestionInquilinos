package com.example.sistemaInquilinos.servicio;

import com.example.sistemaInquilinos.entidad.inquilino;
import com.example.sistemaInquilinos.repositorio.inquilinoRespositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar toda la lógica relacionada con los inquilinos.
 *
 * Actúa como intermediario entre el controlador y el repositorio.
 * Aquí no se accede directamente a la base de datos, eso lo hace el repositorio.
 */
@Service // Le indica a Spring que esta clase es un servicio y debe gestionarla automáticamente
@RequiredArgsConstructor
// Genera automáticamente un constructor con los atributos final
// Gracias a esto, Spring inyecta el repositorio sin necesidad de usar @Autowired
public class inquilinoServicio  {

    /**
     * Repositorio que permite acceder a la base de datos
     * para realizar operaciones sobre la entidad inquilino.
     */
    private final inquilinoRespositorio repositorio;

    /**
     * Devuelve todos los inquilinos existentes en la base de datos.
     */
    public List<inquilino> listaInquilinos(){
        return this.repositorio.findAll();
    }

    /**
     * Busca un inquilino por su ID.
     * Si no existe, devuelve null.
     */
    public inquilino buscarInquilinoPorId(Integer id){
        return repositorio.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo inquilino o actualiza uno ya existente.
     */
    public inquilino guardarInquilino(inquilino inquilino){
        return repositorio.save(inquilino);
    }

    /**
     * Elimina un inquilino de la base de datos usando su ID.
     */
    public void eliminarPorId(Integer id){
        repositorio.deleteById(id);
    }
}
