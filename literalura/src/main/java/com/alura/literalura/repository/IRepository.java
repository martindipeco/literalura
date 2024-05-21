package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(Long isbn);
}
