package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(Long isbn);

    //Consulta nativa de PostGres array_to_string - debo agregar Maven dependency para usar anotación Param
//    @Query(value = "SELECT * FROM Libro l WHERE array_to_string(l.listaIdiomas, ',') LIKE %:idioma%", nativeQuery = true)
//    List<Libro> findPorIdioma(@Param("idioma") String idioma);

    @Query(value = "SELECT * FROM Libro l WHERE l.listaIdiomas LIKE %ES%", nativeQuery = true)
    List<Libro> findPorIdiomaES();

}
