package com.Vetcare.dao;

import com.Vetcare.domain.Mascota;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaDao extends JpaRepository<Mascota, Integer> {
    List<Mascota> findByIdPropietario(Integer idPropietario);
    
    // Buscar mascota por nombre y propietario
    Optional<Mascota> findByIdMascotaAndIdPropietario(int idMascota, Integer idPropietario);
    
    List<Mascota> findByIdMascota(Integer idMascota);
    
    // Obtener el último ID de propietario registrado (manejo de null para evitar errores)
    @Query("SELECT COALESCE(MAX(m.idPropietario), 0) FROM Mascota m")
    Integer obtenerUltimoIdPropietario();
}