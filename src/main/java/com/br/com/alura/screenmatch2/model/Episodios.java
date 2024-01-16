package com.br.com.alura.screenmatch2.model;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodios {
    private Integer temporadas;

    private String titulo;

    private Integer numeroDoEpisodio;

   private double avaliacao;

   private LocalDate dataDeLancamento;

    public Episodios(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporadas = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroDoEpisodio = dadosEpisodio.numero();

        try {
            this.avaliacao = Double.valueOf( dadosEpisodio.avaliacao());
        }
        catch (NumberFormatException exception){
            this.avaliacao = 0;
        }
        try {
            this.dataDeLancamento = LocalDate.parse(dadosEpisodio.dataDeLancamento());

        }
        catch (DateTimeParseException exception){
            this.dataDeLancamento = null;
        }

    }

    public Integer getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDoEpisodio() {
        return numeroDoEpisodio;
    }

    public void setNumeroDoEpisodio(Integer numeroDoEpisodio) {
        this.numeroDoEpisodio = numeroDoEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    @Override
    public String toString() {
        return
                "temporadas=" + temporadas +
                ", titulo='" + titulo + '\'' +
                ", numeroDoEpisodio=" + numeroDoEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataDeLancamento=" + dataDeLancamento;

    }
}
