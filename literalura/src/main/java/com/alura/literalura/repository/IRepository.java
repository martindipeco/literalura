package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
public interface IRepository extends JpaRepository<Libro, Long> {

    //Queries a la base de datos
}
