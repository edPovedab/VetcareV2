package com.Vetcare.Controller;

import com.Vetcare.domain.Usuario;
import com.Vetcare.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        // Verificar si el correo ya existe
        if (usuarioService.findByCorreo(usuario.getCorreo()) != null) {
            redirectAttributes.addFlashAttribute("error", "El correo ya está registrado");
            return "redirect:/registro";
        }

        // Asignar rol por defecto (USUARIO)
        usuario.setRol("USUARIO");

        // Guardar nuevo usuario
        usuarioService.guardar(usuario);

        redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión");
        return "redirect:/login";
    }

    @GetMapping("/bienvenido")
    public String bienvenido(Authentication authentication, Model model) {
        String username = authentication.getName();
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        
        return "bienvenido";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);
        
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

  
}