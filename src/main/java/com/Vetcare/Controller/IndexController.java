package com.Vetcare.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("attribute", "value");
        return "index";
    }

    @RequestMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("attribute", "value");
        return "contacto";
    }

    @RequestMapping("/informacion")
    public String informacion(Model model) {
        model.addAttribute("attribute", "value");
        return "informacion";
    }

    // Cambiar la ruta para evitar conflicto
    @GetMapping("/redirigir_solicitud_citas") // Cambiar la ruta
    public String redirSolicitudCitas() {
        return "/solicitud de citas"; // Asegúrate de que esta vista exista
    }

    @RequestMapping("/negocio")
    public String negocio(Model model) {
        model.addAttribute("attribute", "value");
        return "negocio";
    }

    @RequestMapping("/login")
    public String inicioSesion(Model model) {
        model.addAttribute("attribute", "value");
        return "login";
    }

    @RequestMapping("/productos")
    public String producto(Model model) {
        model.addAttribute("attribute", "value");
        return "productos";
    }
}
