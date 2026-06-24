package com.casoestudio.sistemas.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "sistemas")
public class Sistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 40)
    private String autor;

    @Column(length = 100)
    private String descripcion;

    @Column(name = "anio") 
    private Integer anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;

}