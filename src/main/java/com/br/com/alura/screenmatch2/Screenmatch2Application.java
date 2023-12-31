package com.br.com.alura.screenmatch2;

import com.br.com.alura.screenmatch2.model.DadosSeries;
import com.br.com.alura.screenmatch2.service.ConsumoApi;
import com.br.com.alura.screenmatch2.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CommandLineRunner permite que eu crie chamadas nessa class principal = metodo main
@SpringBootApplication
public class Screenmatch2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatch2Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=d403f677");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
		System.out.println(dados);

	}
}
