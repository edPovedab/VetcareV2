package com.Vetcare.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;
    
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "disponible", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean disponible = true;
    
    // Constructor con id para búsquedas
    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }
    
    // Constructor completo
    public Producto(Integer idProducto, String nombreProducto, String descripcion, 
                   BigDecimal precio, Integer stock, String imagenUrl, 
                   String categoria, Boolean disponible) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.disponible = disponible;
    }
}