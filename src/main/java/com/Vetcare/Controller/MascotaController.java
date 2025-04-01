package com.Vetcare.Controller;

import com.Vetcare.domain.Cita;
import com.Vetcare.domain.Mascota;
import com.Vetcare.domain.Usuario;
import com.Vetcare.service.CitaService;
import com.Vetcare.service.MascotaService;
import com.Vetcare.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CitaService citaService;

    @GetMapping("/listar")
    public String listarMascotas(Model model, Authentication authentication) {
        // Obtener el usuario actual
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Filtrar las mascotas por propietario si el usuario no es admin
        List<Mascota> mascotas;
        if ("ADMIN".equals(usuario.getRol())) {
            mascotas = mascotaService.listarMascotas();
        } else {
            mascotas = mascotaService.buscarPorPropietario(usuario.getIdUsuario());
        }
        
        model.addAttribute("mascotas", mascotas);
        return "mascotas/listar";
    }
    
    @GetMapping("/mis-mascotas")
    public String misMascotas(Model model, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        List<Mascota> mascotas = mascotaService.buscarPorPropietario(usuario.getIdUsuario());
        model.addAttribute("mascotas", mascotas);
        return "mascotas/mis-mascotas";
    }

    @GetMapping("/nueva")
    public String nuevaMascota(Model model, Authentication authentication) {
        Mascota mascota = new Mascota();
        
        // Asignar automáticamente el propietario actual
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario != null) {
            mascota.setIdPropietario(usuario.getIdUsuario());
        }
        
        model.addAttribute("mascota", mascota);
        model.addAttribute("accion", "Registrar");
        
        // Si es admin, mostrar lista de usuarios para seleccionar propietario
        if (usuario != null && "ADMIN".equals(usuario.getRol())) {
            model.addAttribute("usuarios", usuarioService.listarUsuarios());
            model.addAttribute("esAdmin", true);
        } else {
            model.addAttribute("esAdmin", false);
        }
        
        return "mascotas/form";
    }

    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable("id") Integer id, Model model, 
                              Authentication authentication, RedirectAttributes redirectAttributes) {
        Mascota mascota = mascotaService.getMascota(new Mascota(id));
        
        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "La mascota no existe");
            return "redirect:/mascotas/listar";
        }
        
        // Verificar que el usuario sea el propietario o un admin
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        boolean esAdmin = "ADMIN".equals(usuario.getRol());
        boolean esPropietario = mascota.getIdPropietario().equals(usuario.getIdUsuario());
        
        if (!esAdmin && !esPropietario) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar esta mascota");
            return "redirect:/mascotas/mis-mascotas";
        }
        
        model.addAttribute("mascota", mascota);
        model.addAttribute("accion", "Editar");
        
        if (esAdmin) {
            model.addAttribute("usuarios", usuarioService.listarUsuarios());
            model.addAttribute("esAdmin", true);
        } else {
            model.addAttribute("esAdmin", false);
        }
        
        return "mascotas/form";
    }

    @PostMapping("/guardar")
    public String guardarMascota(Mascota mascota, Authentication authentication, 
                               RedirectAttributes redirectAttributes) {
        // Verificar que el usuario sea el propietario o un admin
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        boolean esAdmin = "ADMIN".equals(usuario.getRol());
        
        // Si no es admin y está intentando asignar la mascota a otro propietario
        if (!esAdmin && mascota.getIdPropietario() != null && 
            !mascota.getIdPropietario().equals(usuario.getIdUsuario())) {
            
            mascota.setIdPropietario(usuario.getIdUsuario());
        }
        
        // Si es una nueva mascota y no se especificó propietario, asignar al usuario actual
        if (mascota.getIdPropietario() == null) {
            mascota.setIdPropietario(usuario.getIdUsuario());
        }
        
        mascotaService.guardar(mascota);
        redirectAttributes.addFlashAttribute("success", "Mascota guardada con éxito");
        
        if (esAdmin) {
            return "redirect:/admin/mascotas";
        } else {
            return "redirect:/mascotas/mis-mascotas";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable("id") Integer id, Authentication authentication, 
                                RedirectAttributes redirectAttributes) {
        Mascota mascota = mascotaService.getMascota(new Mascota(id));
        
        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "La mascota no existe");
            return "redirect:/mascotas/listar";
        }
        
        // Verificar que el usuario sea el propietario o un admin
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        boolean esAdmin = "ADMIN".equals(usuario.getRol());
        boolean esPropietario = mascota.getIdPropietario().equals(usuario.getIdUsuario());
        
        if (!esAdmin && !esPropietario) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar esta mascota");
            return "redirect:/mascotas/mis-mascotas";
        }
        
        // Verificar si hay citas asociadas a esta mascota
        List<Cita> citasMascota = citaService.getCitasPorMascota(mascota.getIdMascota());
        if (citasMascota != null && !citasMascota.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", 
                "No se puede eliminar la mascota porque tiene citas programadas. Por favor, cancele las citas primero.");
            return "redirect:/mascotas/mis-mascotas";
        }
        
        mascotaService.eliminar(mascota);
        redirectAttributes.addFlashAttribute("success", "Mascota eliminada con éxito");
        
        if (esAdmin) {
            return "redirect:/admin/mascotas";
        } else {
            return "redirect:/mascotas/mis-mascotas";
        }
    }
    
    @GetMapping("/detalle/{id}")
    public String detalleMascota(@PathVariable("id") Integer id, Model model, 
                               Authentication authentication, RedirectAttributes redirectAttributes) {
        Mascota mascota = mascotaService.getMascota(new Mascota(id));
        
        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "La mascota no existe");
            return "redirect:/mascotas/listar";
        }
        
        // Verificar que el usuario sea el propietario o un admin
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(email);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        boolean esAdmin = "ADMIN".equals(usuario.getRol());
        boolean esPropietario = mascota.getIdPropietario().equals(usuario.getIdUsuario());
        
        if (!esAdmin && !esPropietario) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para ver los detalles de esta mascota");
            return "redirect:/mascotas/mis-mascotas";
        }
        
        // Obtener citas de la mascota
        List<Cita> citasMascota = citaService.getCitasPorMascota(mascota.getIdMascota());
        
        model.addAttribute("mascota", mascota);
        model.addAttribute("propietario", usuarioService.getUsuario(new Usuario(mascota.getIdPropietario())));
        model.addAttribute("citas", citasMascota);
        
        return "mascotas/detalle";
    }
}