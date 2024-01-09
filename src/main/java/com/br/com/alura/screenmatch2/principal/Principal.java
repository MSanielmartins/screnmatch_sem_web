package com.br.com.alura.screenmatch2.principal;
import com.br.com.alura.screenmatch2.model.DadosEpisodio;
import com.br.com.alura.screenmatch2.model.DadosSeries;
import com.br.com.alura.screenmatch2.model.DadosTemporadas;
import com.br.com.alura.screenmatch2.service.ConsumoApi;
import com.br.com.alura.screenmatch2.service.ConverteDados;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    // FINAL = EXIGE UM VALOR, POIS ELE SEMPRE VAI SER UM MESMO
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibirMenu() throws IOException {
        System.out.println("digite o nome da serie para a busca");
        var nomeSerie = leitura.nextLine();
        //replace para trocar um possivel espaço para um + para evitar bug
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
        System.out.println(dados);

        List<DadosTemporadas> temporadas = new ArrayList<>();

        //ADICIONA AS TEMPORADAS EM UMA LISTA
        for (int i = 1; i<= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season=" + i + API_KEY);
            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
            temporadas.add(dadosTemporadas);
        }
        temporadas.forEach(System.out::println);
//
//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();   //vou pegar da da lista de episodeos da temporada i
//            for (int j = 0; j < episodiosTemporadas.size(); i++){
//                System.out.println(episodiosTemporadas.get(i).titulo());         // so pega o titulo de cada episodio
//            }
//        }

        //IMPRIMI OS TITULOS DOS EPISODIOS
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    }
}
