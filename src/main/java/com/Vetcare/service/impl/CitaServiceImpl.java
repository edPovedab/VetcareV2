package com.Vetcare.service.impl;

import com.Vetcare.dao.CitaDao;
import com.Vetcare.dao.MascotaDao;
import com.Vetcare.domain.Cita;
import com.Vetcare.domain.Mascota;
import com.Vetcare.service.CitaService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDao citaRepository;

    @Autowired
    private MascotaDao mascotaDao;

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
        return citaRepository.findById(cita.getIdCita()).orElse(null);
    }

    @Override
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Override
    @Transactional
    public void guardarCita(Cita cita) {
        try {
            Integer idMascota = cita.getIdMascota();
            Integer idPropietario = cita.getIdPropietario();

            if (idMascota != null && idMascota > 0) {
                // Buscar la mascota en la BD
                Optional<Mascota> mascotaOpt = mascotaDao.findById(idMascota);
                if (mascotaOpt.isPresent()) {
                    idPropietario = mascotaOpt.get().getIdPropietario();
                }
            } else {
                // Buscar si ya existe una mascota con el mismo nombre y propietario
                Optional<Mascota> mascotaExistente = mascotaDao.findByIdMascotaAndIdPropietario(idMascota, idPropietario);

                if (mascotaExistente.isPresent()) {
                    idMascota = mascotaExistente.get().getIdMascota();
                    idPropietario = mascotaExistente.get().getIdPropietario();
                } else {
                    // Si no existe, se crea una nueva mascota con un nuevo propietario
                    idPropietario = mascotaDao.obtenerUltimoIdPropietario() + 1;
                    Mascota nuevaMascota = new Mascota();
                    nuevaMascota.setNombreMascota(cita.getNombre());
                    nuevaMascota.setEspecie(cita.getTipoAnimal());
                    nuevaMascota.setIdPropietario(idPropietario);

                    mascotaDao.save(nuevaMascota);
                    idMascota = nuevaMascota.getIdMascota();
                }
            }

            // Asignar ID de mascota a la cita
            cita.setIdMascota(idMascota);

            // Procesar la fecha/hora si está presente
            if (cita.getFechaHora() != null && !cita.getFechaHora().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss]");
                LocalDateTime fechaHoraLocal = LocalDateTime.parse(cita.getFechaHora(), formatter);
                Date fechaCita = Date.from(fechaHoraLocal.atZone(ZoneId.systemDefault()).toInstant());
                cita.setFechaCita(fechaCita);
            }

            // Mantener notas y servicio
            cita.setNotas(cita.getMotivo());
            cita.setServicio(cita.getTipoAnimal());

            // Guardar la cita
            citaRepository.save(cita);

        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error al parsear la fecha: " + e.getMessage());
        }
    }
}
