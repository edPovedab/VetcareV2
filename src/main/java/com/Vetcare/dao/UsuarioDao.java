package com.Vetcare.dao;

import com.Vetcare.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String correo);
    
    // Este método no es seguro para autenticación, solo para referencia
    Usuario findByCorreoAndPassword(String correo, String password);
}