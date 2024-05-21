package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.ILibroRepository;
import com.alura.literalura.service.LibroService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private LibroService libroServicio = new LibroService();

    public Principal(ILibroRepository libroRepositorio, IAutorRepository autorRepository, EntityManager entityManager) {

        this.libroRepository = libroRepositorio;
        this.autorRepository = autorRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void muestraElMenu() {
        System.out.println("\nBienvenidos a Liter-Alura");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    1 - Buscar libro por título y/o autor (y agregarlo a la base de datos propia)
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

                    for(DatoLibro dl : listaDeDatoLibro)
                    {
                        Libro nuevoLibro;
                        Optional<Libro> libroExistente = libroRepository.findByIsbn(Long.valueOf(dl.isbn()));
                        if(libroExistente.isPresent())
                        {
                            //nuevoLibro = libroExistente.get();
                            System.out.println("Ya existe ese libro en la base de datos propia");
                            break;
                        }
                        else
                        {
                            nuevoLibro = new Libro(dl);
                        }

                        for(DatoAutor da : dl.listaAutores())
                        {
                            Autor nuevoAutor;
                            Optional<Autor> autorExistente = autorRepository.findByApellidoNombreAndFechaNacAndFechaMuerte(
                                    da.apellidoNombre(), Integer.valueOf(da.fechaNac()), Integer.valueOf(da.fechaMuerte()));
                            if(autorExistente.isPresent())
                            {
                                nuevoAutor = autorExistente.get();
                                nuevoAutor = entityManager.merge(nuevoAutor);
                            }
                            else
                            {
                                nuevoAutor = new Autor(da);
                            }
                            nuevoAutor.addLibro(nuevoLibro);
                        }
                        libroRepository.save(nuevoLibro);
                    }

                    //TODO: manejar el caso de autores repetidos, o libros repetidos!!

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
