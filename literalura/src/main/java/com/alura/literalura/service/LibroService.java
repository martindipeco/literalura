package com.alura.literalura.service;

import com.alura.literalura.model.Dato;
import com.alura.literalura.model.DatoLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private IRepository repository;

    public List<Libro> buscarLibroPorTitulo(String busqueda)
    {
        String direccion = URL_BASE + busqueda.replace(" ", "%20");
        var json = consumoAPI.obtenerDatos(direccion);
        System.out.println(json); //viene como String
        var datos = conversor.obtenerDatos(json, Dato.class);
        System.out.println(datos); //datos es una lista de DatoLibro

        List<Libro> resultadoBusqueda = new ArrayList<>();
        resultadoBusqueda = datos.listaDeLibros().stream()
                .map(l -> new Libro(l))
                .collect(Collectors.toList());
        //por cada integrante de la lista, instancio un Libro ¿con su respectivo Autor?
        return resultadoBusqueda;
        //TODO convertir a Optional?

    }
}
