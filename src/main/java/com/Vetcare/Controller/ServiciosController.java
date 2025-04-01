package com.Vetcare.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {
    
    @GetMapping
    public String mostrarServicios(Model model) {
        model.addAttribute("titulo", "Nuestros Servicios");
        return "servicios";
    }
    
    @GetMapping("/productos")
    public String productos() {
        return "redirect:/productos";
    }
    
    @GetMapping("/asesorias")
    public String asesorias() {
        return "redirect:/negocio";
    }
    
    @GetMapping("/consultas")
    public String consultas() {
        return "redirect:/contacto";
    }
}