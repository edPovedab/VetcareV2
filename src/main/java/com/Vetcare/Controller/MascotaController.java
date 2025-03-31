package com.Vetcare.Controller;

import com.Vetcare.domain.Mascota;
import com.Vetcare.service.MascotaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/listar_mascotas")
    public String listarMascotas(Model model) {
        List<Mascota> mascotas = mascotaService.listarMascotas();
        model.addAttribute("mascotas", mascotas);
        return "solicitud_citas"; // Asegúrate de que la vista se llama así
    }
}
