package com.example.smartgap.repository;

import com.example.smartgap.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar un usuario por su correo electrónico
    Usuario findByCorreo(String correo);
}
