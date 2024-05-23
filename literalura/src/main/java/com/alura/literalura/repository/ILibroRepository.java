package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(Long isbn);

    //funcion especial array_to_string de PostreSQL
    @Query(value = "SELECT * FROM libros l WHERE array_to_string(l.lista_idiomas, ',') LIKE '%es%'", nativeQuery = true)
    List<Libro> findPorIdiomaES();

    @Query(value = "SELECT * FROM libros l WHERE array_to_string(l.lista_idiomas, ',') LIKE '%pt%'", nativeQuery = true)
    List<Libro> findPorIdiomaPT();

    @Query(value = "SELECT * FROM libros l WHERE array_to_string(l.lista_idiomas, ',') LIKE '%en%'", nativeQuery = true)
    List<Libro> findPorIdiomaEN();

    @Query(value = "SELECT * FROM libros l WHERE array_to_string(l.lista_idiomas, ',') LIKE '%fr%'", nativeQuery = true)
    List<Libro> findPorIdiomaFR();
}
