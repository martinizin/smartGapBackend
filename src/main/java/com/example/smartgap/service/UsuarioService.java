package com.example.smartgap.service;

import com.example.smartgap.model.Usuario;
import com.example.smartgap.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Guardar un usuario (si no existe un usuario con el mismo correo)
    public Usuario guardarUsuario(Usuario usuario) {
        // Validación para comprobar si ya existe un usuario con el mismo correo
        Optional<Usuario> usuarioExistente = Optional.ofNullable(usuarioRepository.findByCorreo(usuario.getCorreo()));
        if (usuarioExistente.isPresent()) {
            // Si el correo ya existe, lanzamos una excepción con un error HTTP adecuado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario por ID
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            // Si no existe un usuario con el ID proporcionado, lanzamos una excepción
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    // Obtener un usuario por correo electrónico
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            // Si no se encuentra el usuario, lanzamos una excepción
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        return usuario;
    }
}
