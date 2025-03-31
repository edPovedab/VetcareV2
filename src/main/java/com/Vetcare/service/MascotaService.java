// MascotaService.java
package com.Vetcare.service;

import com.Vetcare.domain.Mascota;
import java.util.List;

public interface MascotaService {
    List<Mascota> listarMascotas();
    Mascota getMascota(Mascota mascota);
    void guardar(Mascota mascota);
    void eliminar(Mascota mascota);
    List<Mascota> buscarPorId(int idMascota);
    
}