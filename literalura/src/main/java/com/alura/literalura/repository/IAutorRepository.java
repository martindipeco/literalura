package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByApellidoNombreAndFechaNacAndFechaMuerte
            (String apellidoNombre, Integer fechaNac, Integer fechaMuerte);


    //anotacion JPQL
    @Query(value = "SELECT a FROM Autor a WHERE a.fechaNac <= :anio AND a.fechaMuerte >= :anio")
    List<Autor> autoresVivosEnFecha(int anio);
}


