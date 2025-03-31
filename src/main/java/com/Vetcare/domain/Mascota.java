// Mascota.java
package com.Vetcare.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 
    private Integer idMascota; // Cambiado de int a Integer para permitir null
    
    @Column(name = "id_propietario")
    private Integer idPropietario; 
    
    @Column(name = "nombre_mascota")
    private String nombreMascota; 
    
    @Column(name = "especie")
    private String especie;
    
    @Column(name = "raza")
    private String raza;
    
    @Column(name = "edad")
    private Integer edad;

    public Mascota(Integer idMascota) {
        this.idMascota = idMascota;
       
    }

    
    
    
}