package com.Vetcare.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "correo", unique = true)
    private String correo;
    
    @Column(name = "rol")
    private String rol;
    
    // Constructor con parámetros
    public Usuario(Integer idUsuario, String nombreUsuario, String password, String correo, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.correo = correo;
        this.rol = rol;
    }
    
    // Constructor solo con ID para búsquedas
    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}