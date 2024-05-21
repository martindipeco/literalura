package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id //aviso que el atributo que está abajo será la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que sea autoincremental
    private Long id;

    @Column(unique = true)
    private Long isbn; //dato corresponde al id de gutendex recibido en DatoLibro

    private String titulo;

    @ManyToMany(mappedBy = "listaLibros", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) //listaLibros es el atributo en Autor
    private List<Autor> listaAutores;

    private List<String> listaIdiomas;
    private Long cantidadDescargas;

    public Libro(){}

    public Libro(DatoLibro datoLibro)
    {
        this.isbn = datoLibro.isbn();
        this.titulo = datoLibro.titulo();
        this.listaAutores = new ArrayList<>(); //la voy poblando luego - sin datos desde datoLibro
        this.listaIdiomas = datoLibro.listaIdiomas();
        this.cantidadDescargas = datoLibro.cantidadDescargas();
    }

    public Long getId() {
        return id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    public List<String> getListaIdiomas() {
        return listaIdiomas;
    }

    public Long getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setListaAutores(List<Autor> listaAutores) {
        //el método addLibro debe añadir un elemento a la lista
        listaAutores.forEach(autor -> autor.addLibro(this)); //debo setear en ambas entidades para conectar
        this.listaAutores = listaAutores;
    }

    public void addAutor(Autor autor)
    {
        listaAutores.forEach(a -> a.addLibro(this));
        this.listaAutores.add(autor);
    }

    @Override
    public String toString() {
        List<String> listaAutoresString = listaAutores.stream()
                .map(la -> la.toString())
                .collect(Collectors.toList());
        return "\nLibro" +
                "\nISBN: " + isbn +
                "\nTítulo: " + titulo + '\'' +
                "\nAutor(es): " + listaAutoresString +
                "\nIdioma(s): " + listaIdiomas +
                "\nTotal Descargas: " + cantidadDescargas;
    }
}
