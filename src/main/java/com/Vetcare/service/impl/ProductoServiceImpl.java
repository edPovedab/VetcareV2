package com.Vetcare.service.impl;

import com.Vetcare.dao.ProductoDao;
import com.Vetcare.domain.Producto;
import com.Vetcare.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosDisponibles() {
        return productoDao.findByDisponibleTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoDao.findByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoDao.findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Producto producto) {
        productoDao.delete(producto);
    }
}