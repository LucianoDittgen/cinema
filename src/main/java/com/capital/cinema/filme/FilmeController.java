package com.capital.cinema.filme;

import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.util.Scanner;

@Controller
public class FilmeController {

    private final FilmeRepository repository;
    private final Scanner input = new Scanner(System.in);

    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    public void menu() {
        int opcao;
        do {
            System.out.print("\n\"-------  MENU Filme -------\"");
            System.out.print("""
                    
                    1. Inserir novo Filme
                    2. Atualizar um Filme
                    3. Excluir um Filme
                    4. Listar todos os Filmes
                    5. Buscar Filme pelo código
                    6. Buscar Filmes pelo nome
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();

                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        } while (opcao != 0);
    }

    public void inserir() {
        Filme filme = new Filme();
        System.out.println("\n++++++ Cadastro de novo Filme ++++++");
        System.out.print("Digite o nome do Filme: ");
        filme.setTitulo(input.nextLine());
        System.out.print("Digite a duracao do Filme (minutos): ");
        long duracao = input.nextLong();
        input.nextLine();
        filme.setDuracao(Duration.ofMinutes(duracao));
        System.out.println("Filme salvo com sucesso:\n" + repository.save(filme));
    }

    public void atualizar() {
        System.out.println("\n++++++ Alterar um Filme ++++++");
        Filme filme;
        int opcao = 0;
        do {
            System.out.print("Digite o código do Filme (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) return;

            filme = repository.findById(codigo).orElse(null); // Usando método padrão do JpaRepository
            if (filme == null) {
                System.out.println("Código inválido.");
            } else {
                System.out.println("Nome: " + filme.getTitulo());
                System.out.print("Alterar? (1-sim/2-não): ");
                if (input.nextInt() == 1) {
                    input.nextLine();
                    System.out.print("Digite o novo nome: ");
                    filme.setTitulo(input.nextLine());
                }
                System.out.println("Duração: " + filme.getDuracao().toMinutes() + " minutos");
                System.out.print("Alterar? (1-sim/2-não): ");
                if (input.nextInt() == 1) {
                    input.nextLine();
                    System.out.print("Digite a nova duração (em minutos): ");
                    long duracao = input.nextLong();
                    filme.setDuracao(Duration.ofMinutes(duracao));
                }
                System.out.println("Filme atualizado: " + repository.save(filme));
                opcao = 1;
            }
        } while (opcao != 1);
    }
}
