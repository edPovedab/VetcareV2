package com.Vetcare.dao;

import com.Vetcare.domain.Cita;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaDao extends JpaRepository<Cita, Integer> {
    List<Cita> findByIdMascota(Integer idMascota);
    List<Cita> findByIdMascotaIn(List<Integer> idsMascotas);
    List<Cita> findByFechaCitaAfter(Date fecha);
    List<Cita> findByFechaCitaBefore(Date fecha);
}