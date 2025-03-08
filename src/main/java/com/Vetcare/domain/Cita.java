package com.Vetcare.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cita")
@Data
@NoArgsConstructor
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cita;
    
    @Column(name = "id_mascota")
    private Integer id_mascota; // Cambio a Integer para permitir nulos temporalmente
    
    @Column(name = "fecha_cita", nullable = false)
    private Date fecha_cita;
    
    @Column(name = "servicio")
    private String servicio;
    
    @Column(name = "notas")
    private String notas;
    
    // Campos temporales que no se almacenan en la base de datos
    @jakarta.persistence.Transient
    private String tipoAnimal;
    
    @jakarta.persistence.Transient
    private String nombre;
    
    @jakarta.persistence.Transient
    private String fechaHora;
    
    @jakarta.persistence.Transient
    private String sexo;
    
    @jakarta.persistence.Transient
    private String telefono;
    
    @jakarta.persistence.Transient
    private String motivo;
    
    public Cita(int id_mascota, Date fecha_cita, String servicio, String notas) {
        this.id_mascota = id_mascota;
        this.fecha_cita = fecha_cita;
        this.servicio = servicio;
        this.notas = notas;
    }
}
