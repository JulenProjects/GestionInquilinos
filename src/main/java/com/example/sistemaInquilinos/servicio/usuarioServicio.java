package com.example.sistemaInquilinos.servicio;


import com.example.sistemaInquilinos.entidad.usuario;
import com.example.sistemaInquilinos.repositorio.usuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class usuarioServicio {

    private final usuarioRepositorio usuarioRepositorio;

    // LISTAR TODOS LOS USUARIOS
    public List<usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    // BUSCAR USUARIO POR ID
    public usuario buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepositorio.findById(idUsuario).orElse(null);
    }

    // GUARDAR / ACTUALIZAR
    public usuario guardarUsuario(usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    // ELIMINAR
    public void eliminarUsuarioPorId(Integer idUsuario) {
        usuarioRepositorio.deleteById(idUsuario);
    }
}
