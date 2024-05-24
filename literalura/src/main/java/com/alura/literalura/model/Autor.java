package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id //aviso que el atributo que está abajo será la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que sea autoincremental
    private Long id;

    // antes tenia @Column(unique = true), pero puede darse el caso de distintos autores con el mismo nombre! Lamentablemente gutendex no tiene "id" de autor. Habrá que hacer la comparación full de nacimiento y muerte
    private String apellidoNombre;

    private Integer fechaNac;
    private Integer fechaMuerte;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Libro> listaLibros;

    public Autor(){}

    public Autor(DatoAutor datoAutor)
    {
        this.apellidoNombre = datoAutor.apellidoNombre();
        try {
            this.fechaNac = Integer.valueOf(datoAutor.fechaNac());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Sin datos para fecha de nacimiento");
            //¿que dato deberíamos asignar?
            this.fechaNac = null;
        }
        try {
            this.fechaMuerte = Integer.valueOf(datoAutor.fechaMuerte());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Sin datos para fecha de fallecimiento");
            this.fechaMuerte = null;
        }
        this.listaLibros = new ArrayList<>(); //solo inicializo, la lleno más adelante
    }

    public Long getId() {
        return id;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public Integer getFechaNac() {
        return fechaNac;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public void setListaLibros(List<Libro> listaLibros)
    {
        listaLibros.forEach(libro -> libro.addAutor(this));
        this.listaLibros = listaLibros;
    }
    public void addLibro(Libro libro)
    {
        this.listaLibros.add(libro);
        //libro.getListaAutores().add(this);
    }

    @Override
    public String toString() {
        return "\nAutor: " + apellidoNombre +
                "\nNacimiento: " + fechaNac +
                "\nMuerte: " + fechaMuerte;
    }

    public String toStringParaLibro()
    {
        return apellidoNombre;
    }
}
