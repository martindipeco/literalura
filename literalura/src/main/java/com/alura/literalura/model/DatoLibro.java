package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoLibro(
        @JsonAlias("id")Long isbn, //simulo dato de isbn para tratar ejemplares unicos
        @JsonAlias("title")String titulo,
        @JsonAlias("authors") List<DatoAutor> listaAutores,
        @JsonAlias("languages")List<String> listaIdiomas,
        @JsonAlias("download_count")Long cantidadDescargas
) {
}
