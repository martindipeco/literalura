package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.ILibroRepository;
import com.alura.literalura.service.LibroService;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private LibroService libroServicio = new LibroService();

    public Principal(ILibroRepository libroRepositorio, IAutorRepository autorRepositorio) {

        this.libroRepository = libroRepositorio;
        this.autorRepository = autorRepositorio;
    }

    public void muestraElMenu() {
        System.out.println("\nBienvenidos a Liter-Alura");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    1 - Buscar libro por título y/o autor (y agregar a base propia)
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void buscarLibroPorTitulo()
    {
        System.out.println("\nIngrese su búsqueda");
        var busqueda = scanner.nextLine();
        var listaDeDatoLibro = libroServicio.buscarLibroPorTitulo(busqueda);
        if (listaDeDatoLibro.isEmpty())
        {
            System.out.println("No existen datos con esa búsqueda. Intentelo de nuevo");
            return;
        }

        for(DatoLibro dl : listaDeDatoLibro)
        {
            Libro nuevoLibro;
            Optional<Libro> libroExistente = libroRepository.findByIsbn(dl.isbn());
            if(libroExistente.isPresent())
            {
                System.out.println("Ese libro ya existe en la base de datos");
                continue;
            }
            else
            {
                nuevoLibro = new Libro(dl);
            }
            for(DatoAutor da : dl.listaAutores())
            {
                Autor nuevoAutor;
                try
                {
                    Optional<Autor> autorExistente = autorRepository
                            .findByApellidoNombreAndFechaNacAndFechaMuerte(
                                    da.apellidoNombre(), Integer.valueOf(da.fechaNac())
                                    , Integer.valueOf(da.fechaMuerte())
                            );
                    if(autorExistente.isPresent())
                    {
                        //TODO: manejar el caso de autores repetidos!!
                        System.out.println("Ese autor ya existe en la base de datos\n");
                        nuevoAutor = autorExistente.get();
                        //nuevoLibro.addAutor(nuevoAutor);
                        //nuevoAutor.getListaLibros().add(nuevoLibro); //linea anterior ya hace esta accion
                        //se rompe si intento persistir con nuevoAutor.addLibro(nuevoLibro);
                        //Ver como maneja inserciones ScreenMatch
                    }
                    else
                    {
                        nuevoAutor = new Autor(da);
                    }
                    nuevoAutor.getListaLibros().add(nuevoLibro);
                    nuevoLibro.getListaAutores().add(nuevoAutor);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error de dato numérico en Fechas");
                    System.out.println("No se pudo guardar autor");
                    System.out.println(e.getMessage());
                }
            }
            libroRepository.save(nuevoLibro);
        }
    }

    public void listarLibrosRegistrados()
    {
        List<Libro> librosRegistrados = libroRepository.findAll();
        if (librosRegistrados.isEmpty())
        {
            System.out.println("No existen aún registros en la base de datos");
            return;
        }
        librosRegistrados.stream().sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println); //TODO: crear un DTO especifico?
    }

    public void listarAutoresRegistrados()
    {
        List<Autor> autoresRegistrados = autorRepository.findAll();
        if(autoresRegistrados.isEmpty())
        {
            System.out.println("No hay aún autores registrados en la base de datos");
            return;
        }
        autoresRegistrados.stream().sorted(Comparator.comparing(Autor::getApellidoNombre))
                .forEach(System.out::println);
    }

    public void listarAutoresVivosEnFecha()
    {
        System.out.println("\nIngrese el año para saber que autores estaban vivos en esa fecha");
        var busqueda = scanner.nextLine();
        try {
            Integer fecha = Integer.parseInt(busqueda);
            List<Autor> autoresVivosEnFecha = autorRepository.autoresVivosEnFecha(fecha);
            if(autoresVivosEnFecha.isEmpty())
            {
                System.out.println("No se registran autores vivos en esa fecha");
                return;
            }
            autoresVivosEnFecha.stream().sorted(Comparator.comparing(Autor::getApellidoNombre))
                    .forEach(System.out::println);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Dato ingresado no es un número entero. Inténtelo de nuevo");
        }
    }

    public void listarLibrosPorIdioma()
    {
        System.out.println("\nIngrese el idioma");
        var opcion = -1;
        var idioma ="";
        while (opcion != 0) {
            var menu = """
                    
                    1 - ES (Castellano)
                    2 - PT (Portugués)
                    3 - EN (Inglés)
                    4 - FR (Francés)
                                  
                    0 - Volver
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    idioma = "ES";
                    libroRepository.findPorIdiomaES();
                    break;
                case 2:
                    idioma = "PT";
                    break;
                case 3:
                    idioma = "EN";
                    break;
                case 4:
                    idioma = "FR";
                    break;
                case 0:
                    System.out.println("Volviendo...");
                    return;
                default:
                    System.out.println("Opción inválida");
                    return;
            }
//            List<Libro> librosPorIdioma = libroRepository.findPorIdioma(idioma);
//            if (librosPorIdioma.isEmpty())
//            {
//                System.out.println("No existen registros para el idioma " + idioma);
//                return;
//            }
//            librosPorIdioma.stream().sorted(Comparator.comparing(Libro::getTitulo))
//                    .forEach(System.out::println); //TODO: crear un DTO especifico?
        }
    }
}
