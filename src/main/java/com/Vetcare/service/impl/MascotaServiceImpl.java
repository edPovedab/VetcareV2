package com.Vetcare.service.impl;

import com.Vetcare.dao.MascotaDao;
import com.Vetcare.domain.Mascota;
import com.Vetcare.service.MascotaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaDao mascotaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Mascota> listarMascotas() {
        List<Mascota> mascotas = mascotaDao.findAll();
        System.out.println("Mascotas en BD: " + mascotas.size());
        return mascotas;
    }

    @Override
    @Transactional(readOnly = true)
    public Mascota getMascota(Mascota mascota) {
        return mascotaDao.findById(mascota.getIdMascota()).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Mascota mascota) {
        mascotaDao.save(mascota);
    }

    @Override
    @Transactional
    public void eliminar(Mascota mascota) {
        mascotaDao.delete(mascota);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mascota> buscarPorId(int idMascota) {
        return mascotaDao.findByIdMascota(idMascota);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mascota> buscarPorPropietario(Integer idPropietario) {
        return mascotaDao.findByIdPropietario(idPropietario);
    }
}
