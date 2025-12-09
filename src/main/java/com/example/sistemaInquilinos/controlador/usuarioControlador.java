package com.example.sistemaInquilinos.controlador;

import com.example.sistemaInquilinos.entidad.usuario;
import com.example.sistemaInquilinos.servicio.usuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de la gestión de usuarios del sistema.

 * Permite:
 * - Listar usuarios
 * - Buscar usuario por ID
 * - Crear usuario
 * - Actualizar usuario
 * - Eliminar usuario
 *
 * Estos endpoints quedan protegidos solo para ADMIN en este caso.
 */
@RestController
@RequestMapping("/usuarios")
public class usuarioControlador {

    // Logger para mostrar información por consola
    private static final Logger logger = LoggerFactory.getLogger(usuarioControlador.class);

    // Servicio que contiene la lógica de negocio de usuarios
    @Autowired
    private usuarioServicio usuarioServicio;

    /**
     * Obtiene el listado completo de usuarios.
     *
     * URL: GET /usuarios/listado
     */
    @GetMapping("/listado")
    public List<usuario> obtenerUsuarios() {

        List<usuario> lista = usuarioServicio.listarUsuarios();

        logger.info("Listado de usuarios: ");
        lista.forEach(u -> logger.info(u.toString()));

        return lista;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * URL: GET /usuarios/listado/{idUsuario}
     */
    @GetMapping("/listado/{idUsuario}")
    public ResponseEntity<usuario> obtenerUsuarioPorId(@PathVariable Integer idUsuario) {

        usuario user = usuarioServicio.buscarUsuarioPorId(idUsuario);

        if (user == null) {
            logger.warn("Usuario NO encontrado con ID: " + idUsuario);
            return ResponseEntity.notFound().build();
        }

        logger.info("Usuario encontrado: " + user);
        return ResponseEntity.ok(user);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * URL: POST /usuarios/listado/nuevo
     */
    @PostMapping("/listado/nuevo")
    public ResponseEntity<usuario> crearUsuario(@RequestBody usuario usuarioRecibido) {

        logger.info("Usuario recibido para crear: " + usuarioRecibido);

        usuario guardado = usuarioServicio.guardarUsuario(usuarioRecibido);

        return ResponseEntity.ok(guardado);
    }

    /**
     * Actualiza un usuario existente.
     *
     * URL: PUT /usuarios/listado/{idUsuario}
     */
    @PutMapping("/listado/{idUsuario}")
    public ResponseEntity<usuario> actualizarUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody usuario usuarioRecibido) {

        usuario userBD = usuarioServicio.buscarUsuarioPorId(idUsuario);

        if (userBD == null) {
            logger.warn("Usuario NO encontrado para actualizar: " + idUsuario);
            return ResponseEntity.notFound().build();
        }

        // Se actualizan los datos del usuario
        userBD.setUsuario(usuarioRecibido.getUsuario());
        userBD.setPassword(usuarioRecibido.getPassword());
        userBD.setRole(usuarioRecibido.getRole());

        usuario actualizado = usuarioServicio.guardarUsuario(userBD);

        logger.info("Usuario actualizado: " + actualizado);

        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * URL: DELETE /usuarios/listado/{idUsuario}
     */
    @DeleteMapping("/listado/{idUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer idUsuario) {

        usuario user = usuarioServicio.buscarUsuarioPorId(idUsuario);

        if (user == null) {
            logger.warn("Usuario NO encontrado para eliminar: " + idUsuario);
            return ResponseEntity.notFound().build();
        }

        usuarioServicio.eliminarUsuarioPorId(idUsuario);

        logger.info("Usuario eliminado con ID: " + idUsuario);

        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
