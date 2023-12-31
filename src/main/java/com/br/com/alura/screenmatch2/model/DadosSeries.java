package com.br.com.alura.screenmatch2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonAlias = apelido
// @JsonProperty = escreve no json o nome colocado
@JsonIgnoreProperties(ignoreUnknown = true)          // pra ele ignorar os dados que eu não quero, por padrap vem false
public record DadosSeries(
        @JsonAlias("Title")
        String titulo,
        @JsonAlias("totalSeasons")
        Integer totalTemporadas,
        @JsonAlias("imdbRating")
        String avaliacao) {
}
