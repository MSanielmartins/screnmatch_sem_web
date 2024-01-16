package com.br.com.alura.screenmatch2.principal;
import com.br.com.alura.screenmatch2.model.DadosEpisodio;
import com.br.com.alura.screenmatch2.model.DadosSeries;
import com.br.com.alura.screenmatch2.model.DadosTemporadas;
import com.br.com.alura.screenmatch2.model.Episodios;
import com.br.com.alura.screenmatch2.service.ConsumoApi;
import com.br.com.alura.screenmatch2.service.ConverteDados;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
        System.out.println(dados);

        List<DadosTemporadas> temporadas = new ArrayList<>();

        //ADICIONA AS TEMPORADAS EM UMA LISTA
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
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


        // flatMap pra pegar todos os episodeos de todas as temporadas
        // collect pra jogar o resultado em uma nova lista de dadosEpisodios
        // TOLIST DEIXA A LISTA IMUTAVEL
        List<DadosEpisodio> dadosEpisodios = temporadas.stream().
                flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        System.out.println("\ntop 10 episodios");
        //MAP TRANSFORMA
        // FILTER FILTRA
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("primeiro filtro(N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("ordenação " + e))
//                .limit(10)
//                .peek(e -> System.out.println("limit " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("mapeamento " + e))
//                .forEach(System.out::println);

        // GERAR UM EPISODEO QUE VAI TER NO CONTRUTOR
        // O NUMERO DA TEMPORADA E O DADOS EPISODEOS
        //d -> new Episodios(t.numero(), d)
        // uma classe de episodios que vai pegar o num temporada e o dados do ep no construtor

        List<Episodios> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()     //T = TEMPORADA
                        .map(d -> new Episodios(t.numero(), d))  // d = dadosEpisodio
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do titulo do episodio");
//        var trechoTitulo = leitura.nextLine();
//
//        // Optional = pode ou não conbter um valor nulo
//
//        Optional<Episodios> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();   // pega a primeira referencia
//
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get());  //get pra buscar o valor de um optional
//        }else {
//            System.out.println("Episodio não encontrado");
//        }


//
//        System.out.println("apartir de que ano você deseja ver os episodios ?");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataDeBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e ->  e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataDeBusca))
//                .forEach(e -> System.out.println(
//                        "temporada " + e.getTemporadas() +
//                                " Episodio " + e.getTitulo() +
//                                "Data lançamento " + e.getDataDeLancamento().format(formatador)
//                ));
//
        //MAP = CHAVE, VALOR
        //Episodios::getTemporadas = TEMPORADA DO EP
        //Collectors.averagingDouble(Episodios::getAvaliacao) MEDIA DA AVALIACAO
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodios::getTemporadas,
                        Collectors.averagingDouble(Episodios::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodios::getAvaliacao));
        System.out.println("Media" + est.getAverage());
        System.out.println("Melhor ep" + est.getMax());
        System.out.println("pior episodio" + est.getMin());
        System.out.println("quantidade de episodios" + est.getCount() );
    }
}
