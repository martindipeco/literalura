package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//estamos usando las tres propiedades, pero lo agregamos si acaso agregan más
public record DatoAutor(
        @JsonAlias("name") String apellidoNombre,
        @JsonAlias("birth_year") String fechaNac,
        @JsonAlias("death_year") String fechaMuerte
) {
}
