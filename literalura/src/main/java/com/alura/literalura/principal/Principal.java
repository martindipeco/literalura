package com.alura.literalura.principal;

import com.alura.literalura.model.Dato;
import com.alura.literalura.repository.IRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;

import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private IRepository repository;
    private LibroService libroService;

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(IRepository repositorio) {
        this.repository = repositorio;
    }

    public void muestraElMenu() {
        System.out.println("\nBienvenidos a Liter-Alura\n");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
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
                    buscarLibroPorTitulo(busqueda);
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

    public void buscarLibroPorTitulo(String busqueda)
    {
        String direccion = URL_BASE + busqueda.replace(" ", "%20");
        var json = consumoAPI.obtenerDatos(direccion);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Dato.class);
        System.out.println(datos);
    }
}
