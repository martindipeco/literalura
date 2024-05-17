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
    }
}
