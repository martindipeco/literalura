package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id //aviso que el atributo que está abajo será la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que sea autoincremental
    private Long id;

    @Column(unique = true)
    private String apellidoNombre;

    private Integer fechaNac;
    private Integer fechaMuerte;

    @ManyToMany
    private List<Libro> listaLibros;
}
