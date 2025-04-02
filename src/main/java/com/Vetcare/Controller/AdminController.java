package com.Vetcare.Controller;

import com.Vetcare.domain.Mascota;
import com.Vetcare.domain.Usuario;
import com.Vetcare.service.CitaService;
import com.Vetcare.service.MascotaService;
import com.Vetcare.service.ProductoService;
import com.Vetcare.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private MascotaService mascotaService;
    
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Obtener información resumida para el dashboard
        int totalUsuarios = usuarioService.listarUsuarios().size();
        int totalMascotas = mascotaService.listarMascotas().size();
        int totalCitas = citaService.obtenerTodasLasCitas().size();
        int totalProductos = productoService.listarProductos().size();
        
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMascotas", totalMascotas);
        model.addAttribute("totalCitas", totalCitas);
        model.addAttribute("totalProductos", totalProductos);
        
        // Obtener las últimas citas y productos para mostrar
        model.addAttribute("ultimasCitas", citaService.getCitas(true));
        model.addAttribute("ultimosProductos", productoService.listarProductos());
        
        return "dashboard";
    }
    
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "admin/usuarios";
    }
    
    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("accion", "Crear");
        return "admin/usuario-form";
    }
    
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.getUsuario(new Usuario(id));
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "El usuario no existe");
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("accion", "Editar");
        return "admin/usuario-form";
    }
    
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("success", "Usuario guardado correctamente");
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Usuario usuario = new Usuario(id);
        usuarioService.eliminar(usuario);
        redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/mascotas")
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.listarMascotas());
        return "admin/mascotas";
    }
    
    @GetMapping("/mascotas/nuevo")
    public String nuevaMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("accion", "Crear");
        return "admin/mascota-form";
    }
    
    @GetMapping("/mascotas/editar/{id}")
    public String editarMascota(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Mascota mascota = mascotaService.getMascota(new Mascota(id));
        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "La mascota no existe");
            return "redirect:/admin/mascotas";
        }
        model.addAttribute("mascota", mascota);
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("accion", "Editar");
        return "admin/mascota-form";
    }
    
    @PostMapping("/mascotas/guardar")
    public String guardarMascota(Mascota mascota, RedirectAttributes redirectAttributes) {
        mascotaService.guardar(mascota);
        redirectAttributes.addFlashAttribute("success", "Mascota guardada correctamente");
        return "redirect:/admin/mascotas";
    }
    
    @GetMapping("/mascotas/eliminar/{id}")
    public String eliminarMascota(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Mascota mascota = new Mascota(id);
        mascotaService.eliminar(mascota);
        redirectAttributes.addFlashAttribute("success", "Mascota eliminada correctamente");
        return "redirect:/admin/mascotas";
    }
    
    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "admin/productos";
    }
    
    @GetMapping("/citas")
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.obtenerTodasLasCitas());
        return "admin/citas";
    }
}