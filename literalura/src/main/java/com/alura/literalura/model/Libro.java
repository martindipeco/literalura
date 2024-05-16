package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id //aviso que el atributo que está abajo será la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que sea autoincremental
    private Long id;

    @Column(unique = true)
    private Long isbn; //dato corresponde al id de gutendex recibido en DatoLibro

    private String titulo;

    @ManyToMany(mappedBy = "listaLibros", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //listaLibros es el atributo en Autor
    private List<Autor> listaAutores;

    private List<String> listaIdiomas;
    private Long cantidadDescargas;

    public Libro(){}

    public Libro(DatoLibro datoLibro)
    {
        this.isbn = datoLibro.isbn();
        this.titulo = datoLibro.titulo();
        //this.listaAutores = datoLibro.listaAutores();
    }
}
