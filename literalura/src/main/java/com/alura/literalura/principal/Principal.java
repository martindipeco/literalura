package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.ILibroRepository;
import com.alura.literalura.service.LibroService;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;
import java.util.stream.Collectors;

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
                    2 - Buscar por nombre Autor en Repo
                    3 - Listar libros registrados
                    4 - Listar autores registrados
                    5 - Listar autores vivos en un determinado año
                    6 - Listar libros por idioma
                    7 - Estadísticas (API)
                    8 - Estadísticas (Repo)
                    9 - Datos curiosos con años
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            }
            catch (InputMismatchException e)
            {
                opcion = -1;
                scanner.nextLine();
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarAutorPorNombre();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    listarAutoresVivosEnFecha();
                    break;
                case 6:
                    listarLibrosPorIdioma();
                    break;
                case 7:
                    mostrarEstadisticasAPI();
                    break;
                case 8:
                    mostrarEstadisticasRepo();
                    break;
                case 9:
                    mostrarDatosCuriosos();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("\nIngrese su búsqueda");
        var busqueda = scanner.nextLine();
        var listaDeDatoLibro = libroServicio.buscarLibroPorTitulo(busqueda);
        if (listaDeDatoLibro.isEmpty()) {
            System.out.println("No existen datos con esa búsqueda. Intentelo de nuevo");
            return;
        }
        for (DatoLibro dl : listaDeDatoLibro) {
            Libro nuevoLibro;
            Optional<Libro> libroExistente = libroRepository.findByIsbn(dl.isbn());
            if (libroExistente.isPresent()) {
                System.out.println("Ese libro ya existe en la base de datos");
                continue; //proximo: agregar funcionalidad para corregir datos de un libro existente
            } else {
                nuevoLibro = new Libro(dl);
            }
            if(dl.listaAutores().size()>1) //para manejar caso de many autores persisto con libro
            {
                for (DatoAutor da : dl.listaAutores()) {
                    Autor nuevoAutor;
                    try {
                        Optional<Autor> autorExistente = autorRepository
                                .findByApellidoNombreAndFechaNacAndFechaMuerte(
                                        da.apellidoNombre(), Integer.valueOf(da.fechaNac())
                                        , Integer.valueOf(da.fechaMuerte())
                                );
                        if (autorExistente.isPresent()) {
                            nuevoAutor = autorExistente.get();
                        } else {
                            nuevoAutor = new Autor(da);
                        }
                        nuevoAutor.getListaLibros().add(nuevoLibro);
                        nuevoLibro.getListaAutores().add(nuevoAutor);

                    } catch (NumberFormatException e) {
                        System.out.println("Error de dato numérico en Fechas");
                        System.out.println("No se pudo guardar autor");
                        System.out.println(e.getMessage());
                    }
                }
                libroRepository.save(nuevoLibro);
            }
            else if (dl.listaAutores().size()==1)//tengo el caso de un único autor. persisto por autor
            {
                var datoAutorUnico = dl.listaAutores().get(0);
                Autor nuevoAutor;
                try {
                    Optional<Autor> autorExistente = autorRepository
                            .findByApellidoNombreAndFechaNacAndFechaMuerte(
                                    datoAutorUnico.apellidoNombre(), Integer.valueOf(datoAutorUnico.fechaNac())
                                    , Integer.valueOf(datoAutorUnico.fechaMuerte())
                            );
                    if (autorExistente.isPresent()) {
                        nuevoAutor = autorExistente.get();
                    } else {
                        nuevoAutor = new Autor(datoAutorUnico);
                    }
                    nuevoAutor.getListaLibros().add(nuevoLibro);
                    nuevoLibro.getListaAutores().add(nuevoAutor);
                    autorRepository.save(nuevoAutor);

                } catch (NumberFormatException e) {
                    System.out.println("Error de dato numérico en Fechas");
                    System.out.println("No se pudo guardar autor");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void buscarAutorPorNombre()
    {
        System.out.println("\nIngrese su búsqueda");
        var busqueda = scanner.nextLine();
        var autorBuscado = autorRepository.findByApellidoNombreContainsIgnoreCase(busqueda);
        if(autorBuscado.isPresent())
        {
            System.out.println("Se encontró a " + autorBuscado.get());
        }
        else
        {
            System.out.println("No se encontró ningún autor con ese nombre");
        }

    }
    private void listarLibrosRegistrados() {
        List<Libro> librosRegistrados = libroRepository.findAll();
        if (librosRegistrados.isEmpty()) {
            System.out.println("No existen aún registros en la base de datos");
            return;
        }
        librosRegistrados.stream().sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autoresRegistrados = autorRepository.findAll();
        if (autoresRegistrados.isEmpty()) {
            System.out.println("No hay aún autores registrados en la base de datos");
            return;
        }
        autoresRegistrados.stream().sorted(Comparator.comparing(Autor::getApellidoNombre))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEnFecha() {
        System.out.println("\nIngrese el año para saber que autores estaban vivos en esa fecha");
        var busqueda = scanner.nextLine();
        try {
            Integer fecha = Integer.parseInt(busqueda);
            List<Autor> autoresVivosEnFecha = autorRepository.autoresVivosEnFecha(fecha);
            if (autoresVivosEnFecha.isEmpty()) {
                System.out.println("No se registran autores vivos en esa fecha");
                return;
            }
            autoresVivosEnFecha.stream().sorted(Comparator.comparing(Autor::getApellidoNombre))
                    .forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Dato ingresado no es un número entero. Inténtelo de nuevo");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\nIngrese el idioma");
        var opcion = -1;
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
            List<Libro> librosPorIdioma = new ArrayList<>();

            switch (opcion) {
                case 1:
                    librosPorIdioma = libroRepository.findPorIdiomaES();
                    break;
                case 2:
                    librosPorIdioma = libroRepository.findPorIdiomaPT();
                    break;
                case 3:
                    librosPorIdioma = libroRepository.findPorIdiomaEN();
                    break;
                case 4:
                    librosPorIdioma = libroRepository.findPorIdiomaFR();
                    break;
                case 0:
                    System.out.println("Volviendo...");
                    return;
                default:
                    System.out.println("Opción inválida");
                    return;
            }
            if (librosPorIdioma.isEmpty())
            {
                System.out.println("No existen registros para ese idioma");
                return;
            }
            librosPorIdioma.stream().sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }

    private void mostrarEstadisticasAPI()
    {
        System.out.println("En base a 32 casos más populares de la API");
        var datos = libroServicio.traerTodaLaBase();
        var libros = datos.stream().map(Libro::new).collect(Collectors.toList());
        mostrarEstadisticas(libros);
    }

    private void mostrarEstadisticasRepo()
    {
        System.out.println("En base al total de casos de la base propia");
        var libros = libroRepository.findAll();
        mostrarEstadisticas(libros);
    }

    private void mostrarEstadisticas(List<Libro> libros)
    {
        LongSummaryStatistics est = libros.stream()
                .filter(d -> d.getCantidadDescargas()>0)
                .collect(Collectors.summarizingLong(Libro::getCantidadDescargas));

        System.out.println("Promedio de descargas: " + est.getAverage());
        System.out.println("Cantidad de casos: " + est.getCount());
        System.out.println("Mínima de descargas: " + est.getMin());
        System.out.println("Máxima de descargas: " + est.getMax());
        int top = 10;
        if (libros.size()<top)
        {
            top = libros.size();
        }
        System.out.println("Top " + top + " más descargados: ");

        for (int i= 0; i<top; i++)
        {
            System.out.println("\n");
            System.out.println("Pesto nº "+(i+1));
            System.out.println(libros.get(i));
        }
    }

    private void mostrarDatosCuriosos()
    {
        Optional<Autor> elMasLongevo = autorRepository.autorMasLongevo();
        if(elMasLongevo.isPresent())
        {
            Autor masLongevoPresente = elMasLongevo.get();
            System.out.println("El autor más longevo de acuerdo con la base fue: ");
            System.out.println(masLongevoPresente);
            System.out.println("Vivió " + (masLongevoPresente.getFechaMuerte() - masLongevoPresente.getFechaNac())
                    + " años");
        }
        else
        {
            System.out.println("No se encontraron datos de longevidad");
        }
    }
}
