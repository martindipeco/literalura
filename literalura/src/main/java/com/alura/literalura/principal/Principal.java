package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.IRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private IRepository repository;
    private IAutorRepository autorRepository;
    private LibroService libroServicio = new LibroService();

    public Principal(IRepository repositorio, IAutorRepository autorRepositorio) {

        this.repository = repositorio;
        this.autorRepository = autorRepositorio;
    }

    public void muestraElMenu() {
        System.out.println("\nBienvenidos a Liter-Alura");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    1 - Buscar libro por título y/o autor
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
                    System.out.println("\nIngrese su búsqueda");
                    var busqueda = scanner.nextLine();
                    var listaDeDatoLibro = libroServicio.buscarLibroPorTitulo(busqueda);
                    if (listaDeDatoLibro.isEmpty())
                    {
                        System.out.println("No existen datos con esa búsqueda. Intentelo de nuevo");
                        break;
                    }

                    for(DatoLibro dl : listaDeDatoLibro)
                    {
                        Libro nuevoLibro;
                        Optional<Libro> libroExistente = repository.findByIsbn(dl.isbn());
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
                                    System.out.println("Ese autor ya existe en la base de datos\n");
                                    nuevoAutor = autorExistente.get();
                                    //nuevoLibro.addAutor(nuevoAutor);
                                    nuevoAutor.getListaLibros().add(nuevoLibro); //linea anterior ya hace esta accion
                                    //se rompe si intento persistir con nuevoAutor.addLibro(nuevoLibro);
                                    //Ver como maneja inserciones ScreenMatch
                                }
                                else
                                {
                                    nuevoAutor = new Autor(da);
                                    nuevoAutor.addLibro(nuevoLibro);
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Error de dato numérico en Fechas");
                                System.out.println("No se pudo guardar autor");
                                System.out.println(e.getMessage());
                            }
                        }
                        repository.save(nuevoLibro);
                    }

                    //TODO: manejar el caso de autores repetidos, o libros repetidos!!

                    //podria ser algo así

//                    public Autor saveOrUpdateAutor(DatoAutor datoAutor) {
//                        Optional<Autor> existingAutor = autorRepository.findByApellidoNombre(datoAutor.apellidoNombre());
//                        if (existingAutor.isPresent()) {
//                            return existingAutor.get();
//                        } else {
//                            Autor autor = new Autor(datoAutor);
//                            return autorRepository.save(autor);
//                        }
//                    }


                    break;
                case 2:
                    //buscarEpisodioPorSerie();
                    break;
                case 3:
                    //mostrarSeriesBuscadas();
                    break;
                case 4:
                    //buscarSeriesPorTitulo();
                    break;
                case 5:
                    //listarTop5();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

}
