package com.Vetcare.service.impl;

import com.Vetcare.dao.CitaDao;
import com.Vetcare.domain.Cita;
import com.Vetcare.service.CitaService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDao citaRepository;

    @Override
    public Cita crearCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void save(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }

    @Override
    public List<Cita> getCitas(boolean activas) {
        
        return citaRepository.findAll();
    }

    @Override
    public Cita getCita(Cita cita) {
        return citaRepository.findById(cita.getId_cita()).orElse(null);
    }

    @Override
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Override
    public void guardarCita(Cita cita) {
        try {
            if (cita.getFechaHora() != null && !cita.getFechaHora().isEmpty()) {
                LocalDateTime fechaHoraLocal = LocalDateTime.parse(cita.getFechaHora());
                Date fechaCita = Date.from(fechaHoraLocal.atZone(ZoneId.systemDefault()).toInstant());
                cita.setFecha_cita(fechaCita);
            }

            cita.setId_mascota(1);
            cita.setNotas(cita.getMotivo());
            cita.setServicio(cita.getTipoAnimal());

            citaRepository.save(cita);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error al parsear la fecha: " + e.getMessage());
        }
    }
}
