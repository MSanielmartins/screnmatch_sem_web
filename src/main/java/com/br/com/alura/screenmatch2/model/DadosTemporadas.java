package com.br.com.alura.screenmatch2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporadas(
        @JsonAlias("Season")
        Integer numero,
        @JsonAlias("Episodes")
        List<DadosEpisodio> episodios) {

}