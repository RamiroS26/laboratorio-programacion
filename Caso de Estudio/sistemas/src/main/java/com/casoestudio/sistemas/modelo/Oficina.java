package com.casoestudio.sistemas.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "oficinas")
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL)
    private List<Sistema> sistemas;


}