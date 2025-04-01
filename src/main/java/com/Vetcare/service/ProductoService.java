package com.Vetcare.service;

import com.Vetcare.domain.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarProductos();
    List<Producto> listarProductosDisponibles();
    List<Producto> buscarPorCategoria(String categoria);
    List<Producto> buscarPorNombre(String nombre);
    Producto getProducto(Producto producto);
    void guardar(Producto producto);
    void eliminar(Producto producto);
}