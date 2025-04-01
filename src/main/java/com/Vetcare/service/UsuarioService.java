package com.Vetcare.service;

import com.Vetcare.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario autenticarUsuario(String correo, String password);
    Usuario findByCorreo(String correo);
    List<Usuario> listarUsuarios();
    Usuario getUsuario(Usuario usuario);
    void guardar(Usuario usuario);
    void eliminar(Usuario usuario);
}