package com.Vetcare.service.impl;

import com.Vetcare.dao.UsuarioDao;
import com.Vetcare.domain.Usuario;
import com.Vetcare.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario autenticarUsuario(String correo, String password) {
        Usuario usuario = usuarioDao.findByCorreo(correo);
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioDao.findByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Usuario usuario) {
        // Si es un nuevo usuario o se está actualizando la contraseña
        if (usuario.getIdUsuario() == null || 
            (usuario.getPassword() != null && !usuario.getPassword().isEmpty() && 
             !usuario.getPassword().startsWith("$2a$"))) {
            
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Si estamos actualizando un usuario pero no la contraseña
            Usuario existente = usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
            if (existente != null) {
                usuario.setPassword(existente.getPassword());
            }
        }
        
        // Asignar rol por defecto si no se especifica
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USUARIO");
        }
        
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Usuario usuario) {
        usuarioDao.delete(usuario);
    }
}