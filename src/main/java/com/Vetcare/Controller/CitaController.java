package com.Vetcare.Controller;

import com.Vetcare.domain.Cita;
import com.Vetcare.domain.Mascota;
import com.Vetcare.domain.Usuario;
import com.Vetcare.service.CitaService;
import com.Vetcare.service.MascotaService;
import com.Vetcare.service.UsuarioService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final MascotaService mascotaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public CitaController(CitaService citaService, MascotaService mascotaService) {
        this.citaService = citaService;
        this.mascotaService = mascotaService;
    }

    @GetMapping("/listar")
    public String mostrarFormularioCitas(Model model, Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);

        var citas = citaService.getCitas(true);
        model.addAttribute("citas", citas);
        model.addAttribute("usuario", usuario);
        return "citas_programadas";
    }

    @GetMapping("/solicitud")
    public String mostrarFormularioSolicitud(Model model, Authentication authentication) {
        // Obtener el usuario autenticado actual
        String correo = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(correo);

        if (usuario != null) {
            // Obtener mascotas del usuario
            List<Mascota> mascotas = mascotaService.buscarPorPropietario(usuario.getIdUsuario());
            model.addAttribute("mascotas", mascotas);
            model.addAttribute("usuario", usuario); // Añadir el usuario al modelo
        } else {
            model.addAttribute("error", "No se pudo obtener la información del usuario");
        }

        return "solicitud de citas";
    }

    @PostMapping("/solicitar")
    public String solicitarCita(@RequestParam(required = false) Integer idMascota,
            @RequestParam(required = false) String tipoAnimal,
            @RequestParam(required = false) String nombre,
            @RequestParam String fechaHora,
            @RequestParam(required = false) String sexo,
            @RequestParam String telefono,
            @RequestParam String motivo,
            @RequestParam(required = false) String notas,
            @RequestParam(required = false) Integer idPropietario, // Añade este parámetro
            RedirectAttributes redirectAttributes) {

        try {
            Cita nuevaCita = new Cita();

            // Configurar la fecha
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date fechaCita = formatter.parse(fechaHora);
                nuevaCita.setFechaCita(fechaCita);
            } catch (ParseException e) {
                nuevaCita.setFechaCita(new Date());
            }

            nuevaCita.setTelefono(telefono);
            nuevaCita.setNotas(notas);
            nuevaCita.setMotivo(motivo);

            if (idMascota != null && idMascota > 0) {
                // Usar mascota existente
                Mascota mascota = mascotaService.getMascota(new Mascota(idMascota));
                if (mascota != null) {
                    nuevaCita.setIdMascota(mascota.getIdMascota());
                    nuevaCita.setNombre(mascota.getNombreMascota());
                    nuevaCita.setTipoAnimal(mascota.getEspecie());
                }
            } else {
                // Crear nueva mascota
                if (tipoAnimal == null || nombre == null || sexo == null || idPropietario == null) {
                    redirectAttributes.addFlashAttribute("error", "Debes proporcionar información completa para una nueva mascota, incluyendo el propietario.");
                    return "redirect:/citas/solicitud";
                }

                // Crear y guardar la nueva mascota
                Mascota nuevaMascota = new Mascota();
                nuevaMascota.setNombreMascota(nombre);
                nuevaMascota.setEspecie(tipoAnimal);
                nuevaMascota.setIdPropietario(idPropietario); // Este debe ser un ID válido en la tabla usuarios

                mascotaService.guardar(nuevaMascota);

                // Asociar la mascota a la cita
                nuevaCita.setIdMascota(nuevaMascota.getIdMascota());
                nuevaCita.setNombre(nuevaMascota.getNombreMascota());
                nuevaCita.setTipoAnimal(nuevaMascota.getEspecie());
            }

            citaService.guardarCita(nuevaCita);
            redirectAttributes.addFlashAttribute("success", "Cita programada con éxito");
            return "redirect:/citas/listar";

        } catch (Exception e) {
            System.err.println("Error al guardar cita: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar la cita: " + e.getMessage());
            return "redirect:/citas/solicitud";
        }
    }

    @GetMapping("/nueva")
    public String nuevaCitaForm(Model model) {
        model.addAttribute("cita", new Cita());
        return "formCita";
    }

    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Cita cita = citaService.getCita(new Cita(id));
        if (cita == null) {
            redirectAttributes.addFlashAttribute("error", "Cita no encontrada");
            return "redirect:/citas/listar";
        }
        model.addAttribute("cita", cita);
        return "formCita";
    }

    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttributes) {
        citaService.guardarCita(cita);
        redirectAttributes.addFlashAttribute("success", "Cita guardada correctamente");
        return "redirect:/citas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Cita cita = new Cita(id);
        citaService.delete(cita);
        redirectAttributes.addFlashAttribute("success", "Cita eliminada correctamente");
        return "redirect:/citas/listar";
    }
}
