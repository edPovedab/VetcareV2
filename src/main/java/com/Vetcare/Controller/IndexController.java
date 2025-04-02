package com.Vetcare.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/bienvenido")
    public String index(Model model) {
        model.addAttribute("titulo", "Inicio - VetCare+");
        return "index";
    }

    @RequestMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("titulo", "Contáctenos - VetCare+");
        return "contacto";
    }

    @RequestMapping("/informacion")
    public String informacion(Model model) {
        model.addAttribute("titulo", "Acerca de nosotros - VetCare+");
        return "informacion";
    }

    // Redirección para solicitud de citas
    @GetMapping("/agendar-cita")
    public String agendarCita(Authentication authentication) {
        // Si el usuario está autenticado, ir directamente a la página de solicitud de citas
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/citas/solicitud";
        } else {
            // Si no está autenticado, redirigir al login
            return "redirect:/login";
        }
    }

    @RequestMapping("/negocio")
    public String negocio(Model model) {
        model.addAttribute("titulo", "Asesorías - VetCare+");
        return "negocio";
    }

    @RequestMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("titulo", "Productos - VetCare+");
        return "productos";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "error/acceso-denegado";
    }
}
