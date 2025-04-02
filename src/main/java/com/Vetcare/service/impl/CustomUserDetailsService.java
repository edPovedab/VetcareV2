package com.Vetcare.service.impl;
import com.Vetcare.dao.UsuarioDao;
import com.Vetcare.domain.Usuario;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
        }
        
        // Asegurarse que el rol tenga el prefijo ROLE_ si es necesario para que funcione a la hora de verlo en la base de datos
        String rol = usuario.getRol();
        if (!rol.startsWith("ROLE_") && !rol.equals("ADMIN") && !rol.equals("USUARIO")) {
            rol = "ROLE_" + rol;
        }
        
        System.out.println("Usuario encontrado: " + usuario.getCorreo() + ", Rol: " + rol);
        
        return new User(
            usuario.getCorreo(),
            usuario.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(rol))
        );
    }
}