package com.capital.cinema;

import com.capital.cinema.atua.AtuaController;
import com.capital.cinema.filme.FilmeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class HomeController {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HomeController.class, args);

		Scanner input = new Scanner(System.in);
		int opcao;

		// Pegando os controllers gerenciados pelo Spring
		FilmeController filmeController = context.getBean(FilmeController.class);
		AtuaController atuaController = context.getBean(AtuaController.class);

		do {
			System.out.print("\n-------  Registro de Filme -------");
			System.out.print(
					"""
    
                        1. Manter Filme
                        2. Manter Atuação
                        Opção (Zero p/sair):\s""");
			opcao = input.nextInt();
			input.nextLine();

			switch (opcao) {
				case 1 -> filmeController.menu();
				case 2 -> atuaController.menu();
				default -> {
					if (opcao != 0) System.out.println("Opção inválida.");
				}
			}

		} while (opcao != 0);

		System.out.println("\n\n!!!!!!!! Fim da aplicação !!!!!!!!");
		input.close();
	}
}
