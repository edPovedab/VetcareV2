package com.Vetcare.dao;

import com.Vetcare.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoDao extends JpaRepository<Producto, Integer> {
    List<Producto> findByDisponibleTrue();
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombre);
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
}