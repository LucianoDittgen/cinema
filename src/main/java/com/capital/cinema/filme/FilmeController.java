package com.capital.cinema.filme;

import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.util.List;
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
                case 3 -> excluir();
                case 4 -> selectTodos();
                case 5 -> selectFilmeById();
                case 6 -> selectFilmeByNome();
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
        int duracao = input.nextInt();
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

            filme = repository.findById(codigo).orElse(null);
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
                    int duracao = input.nextInt();
                    filme.setDuracao(Duration.ofMinutes(duracao));
                }
                System.out.println("Filme atualizado: " + repository.save(filme));
                opcao = 1;
            }
        } while (opcao != 1);
    }

    public void excluir() {
        System.out.println("\n++++++ Excluir um Filme ++++++");
        Filme filme;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do Filme (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if (codigo > 0) {
                filme = repository.getFilmeById(codigo);
                if (filme == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(filme);
                    System.out.print("Excluir este Filme? (1-sim/2-não) ");
                    if (input.nextInt() == 1) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (1-sim/2-não) ");
                        input.nextLine();
                        repository.delete(filme);
                        System.out.println("Filme excluído com sucesso:" + filme);
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    private void selectTodos() {
        System.out.println("\nLista de Filmes cadastrados no banco de dados:\n" + repository.findAll());
    }

    public void selectFilmeById() {
        System.out.print("\nDigite o código do Filme: ");
        Filme filme = repository.getFilmeById(input.nextLong());
        input.nextLine();
        if (filme != null) {
            System.out.println(filme);
        } else {
            System.out.println("Código não localizado.");
        }
    }

    public void selectFilmeByNome() {
        System.out.print("Digite o nome do Filme: ");
        String nome = input.nextLine();
        System.out.println("Chave de pesquisa: " + nome);

        List<Filme> filmes = repository.getFilmesByTitulo(nome);

        if (filmes.isEmpty()) {
            System.out.println("Não há registros correspondentes para: " + nome);
        } else {
            System.out.println("Filmes encontrados:");
            for (Filme f : filmes) {
                System.out.println(f);
            }
        }
    }
}
