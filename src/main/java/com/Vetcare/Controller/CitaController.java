package com.Vetcare.controller;

import com.Vetcare.domain.Cita;
import com.Vetcare.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
   
    
    @GetMapping("/citas_programadas")
    public String mostrarFormularioCitas(Model model) {
        var citas =citaService.getCitas(true);
       
        model.addAttribute("citas", citas);
        return "citas_programadas"; // Nombre de la vista HTML
    }
    
    @PostMapping("/solicitar")
    public String solicitarCita(@RequestParam String tipoAnimal, 
                                @RequestParam String nombre, 
                                @RequestParam String fechaHora, 
                                @RequestParam String sexo, 
                                @RequestParam String telefono, 
                                @RequestParam String motivo,
                                RedirectAttributes redirectAttributes) {
        
        Cita nuevaCita = new Cita();
        nuevaCita.setTipoAnimal(tipoAnimal);
        nuevaCita.setNombre(nombre);
        nuevaCita.setFechaHora(fechaHora);
        nuevaCita.setSexo(sexo);
        nuevaCita.setTelefono(telefono);
        nuevaCita.setMotivo(motivo);
        
        citaService.guardarCita(nuevaCita);
        
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:citas_programadas";
    }
}

