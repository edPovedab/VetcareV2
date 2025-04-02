package com.Vetcare.Controller;

import com.Vetcare.domain.Mascota;
import com.Vetcare.domain.Usuario;
import com.Vetcare.service.MascotaService;
import com.Vetcare.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MascotaService mascotaService;

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
        try {
            // Verificar si el correo ya existe
            if (usuarioService.findByCorreo(usuario.getCorreo()) != null) {
                redirectAttributes.addFlashAttribute("error", "El correo ya está registrado");
                return "redirect:/registro";
            }

            // Asignar rol por defecto
            usuario.setRol("USUARIO");

            // Guardar nuevo usuario - el servicio se encarga de codificar la contraseña
            usuarioService.guardar(usuario);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión");

            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "redirect:/registro";
        }
    }

    @GetMapping("/bienvenido")
    public String bienvenido(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        String username = authentication.getName();

        // Verificar si es admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN") || role.equals("ROLE_ADMIN"));

        // Obtener el usuario actual
        Usuario usuario = usuarioService.findByCorreo(username);

        // Si el usuario existe, obtener sus mascotas
        if (usuario != null && !isAdmin) {
            List<Mascota> mascotas = mascotaService.buscarPorPropietario(usuario.getIdUsuario());
            model.addAttribute("mascotas", mascotas);
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);

        return "bienvenido";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        String correo = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/bienvenido";
        }

        // Obtener las mascotas del usuario
        List<Mascota> mascotas = mascotaService.buscarPorPropietario(usuario.getIdUsuario());

        model.addAttribute("usuario", usuario);
        model.addAttribute("mascotas", mascotas);

        return "perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(Usuario usuario,
            @RequestParam(required = false) String newPassword,
            @RequestParam(required = false) String confirmPassword,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            
            // Obtener el usuario actual
            String correo = authentication.getName();
            Usuario usuarioActual = usuarioService.findByCorreo(correo);

            if (usuarioActual == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/login";
            }

            // Verificar que el ID coincida con el usuario autenticado
            if (!usuarioActual.getIdUsuario().equals(usuario.getIdUsuario())) {
                redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este perfil");
                return "redirect:/perfil";
            }

            // Actualizar el nombre si se ha cambiado
            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty()) {
                usuarioActual.setNombreUsuario(usuario.getNombreUsuario());
            }

            // Actualizar la contraseña si se ha proporcionado
            if (newPassword != null && !newPassword.isEmpty()) {
                // Verificar que las contraseñas coincidan
                if (!newPassword.equals(confirmPassword)) {
                    redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
                    return "redirect:/perfil";
                }

                usuarioActual.setPassword(newPassword);
            }

            // Guardar los cambios
            usuarioService.guardar(usuarioActual);

            redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente");
            return "redirect:/perfil";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "redirect:/perfil";
        }
    }
}