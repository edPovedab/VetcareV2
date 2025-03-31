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
@Table(name = "cita") // Asegúrate de que el nombre de la tabla sea correcto
@Data
@NoArgsConstructor
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCita; // Cambiado a camelCase
    
    @Column(name = "id_mascota")
    private int idMascota; // Cambiado a camelCase
    
    @Column(name = "fecha_cita", nullable = false)
    private Date fechaCita; // Cambiado a camelCase
    
    @Column(name = "servicio")
    private String servicio;
    
    @Column(name = "notas")
    private String notas;
    
    @Column(name = "id_propietario")
    private int idPropietario;
    
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
    
    public Cita(int idMascota, Date fechaCita, String servicio, String notas) {
        this.idMascota = idMascota;
        this.fechaCita = fechaCita;
        this.servicio = servicio;
        this.notas = notas;
    }

    public Cita(int idCita) {
        this.idCita = idCita;
    }
    
}
