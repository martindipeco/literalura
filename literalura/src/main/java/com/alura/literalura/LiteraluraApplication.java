package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.ILibroRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private ILibroRepository libroRepository;

	@Autowired
	private IAutorRepository autorRepository;
	@Autowired
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository, entityManager);
		principal.muestraElMenu();
	}
}


