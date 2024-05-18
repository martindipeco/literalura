package com.alura.literalura.service;

import com.alura.literalura.model.Dato;
import com.alura.literalura.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private IRepository repository;

    public void buscarLibroPorTitulo(String busqueda)
    {
        String direccion = URL_BASE + busqueda.replace(" ", "%20");
        var json = consumoAPI.obtenerDatos(direccion);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Dato.class);
        System.out.println(datos);
    }
}
