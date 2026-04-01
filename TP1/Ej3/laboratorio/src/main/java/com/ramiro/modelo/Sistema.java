package com.ramiro.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sistema")
public class Sistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String autor;

    @Column(nullable = true, length = 100)
    private String descripcion;

    @Column(nullable = false, length = 4)
    private int año;
}
