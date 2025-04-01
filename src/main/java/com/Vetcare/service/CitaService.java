package com.Vetcare.service;

import com.Vetcare.domain.Cita;
import java.util.List;

public interface CitaService {
    Cita crearCita(Cita cita);
    void save(Cita cita);
    void delete(Cita cita);
    List<Cita> getCitas(boolean activas);
    Cita getCita(Cita cita);
    List<Cita> obtenerTodasLasCitas();
    void guardarCita(Cita cita);
    List<Cita> getCitasPorMascota(Integer idMascota);
    List<Cita> getCitasPorPropietario(Integer idPropietario);
}