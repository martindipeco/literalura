package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private final String URL_BASE_ID = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public List<DatoLibro> buscarLibroPorTitulo(String busqueda)
    {
        String direccion = URL_BASE + busqueda.replace(" ", "%20");
        var json = consumoAPI.obtenerDatos(direccion);
        System.out.println(json); //viene como String
        var datos = conversor.obtenerDatos(json, Dato.class);
        System.out.println(datos); //datos contiene como único atributo una lista de DatoLibro
        return datos.listaDeLibros();
    }

    public List<Autor> buscaAutoresDeLibroPorIsbn(Long isbn){
        String direccion = URL_BASE_ID + isbn;
        var json = consumoAPI.obtenerDatos(direccion);
        var datos = conversor.obtenerDatos(json, Dato.class);
        List<Autor> autores = new ArrayList<>();
        for (DatoLibro datoLibro : datos.listaDeLibros())
        {
            autores = datoLibro.listaAutores().stream()
                    .map(la -> new Autor(la))
                    .collect(Collectors.toList());
        }
        return autores;
    }

}
