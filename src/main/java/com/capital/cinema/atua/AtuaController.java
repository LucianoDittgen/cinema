package com.capital.cinema.atua;

import com.capital.cinema.filme.Filme;
import com.capital.cinema.filme.FilmeRepository;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AtuaController {

    private final AtuaRepository atuaRepository;
    private final FilmeRepository filmeRepository;
    private final Scanner input = new Scanner(System.in);

    public AtuaController(AtuaRepository atuaRepository, FilmeRepository filmeRepository) {
        this.atuaRepository = atuaRepository;
        this.filmeRepository = filmeRepository;
    }

    public void menu() {
        int opcao;
        do {
            System.out.print("\n\"-------  MENU Atuação -------\"");
            System.out.print("""
                    
                    1. Inserir nova Atuação
                    2. Atualizar Atuação
                    3. Listar Atuações
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
                case 3 -> listar();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        } while (opcao != 0);
    }

    public void inserir() {
        Atua atua = new Atua();
        System.out.println("\n++++++ Cadastro de nova Atuação ++++++");

        System.out.print("Digite o papel do ator: ");
        atua.setPapel(input.nextLine());

        // Listar filmes existentes
        System.out.println("\nFilmes disponíveis:");
        filmeRepository.findAll().forEach(f ->
                System.out.println("ID: " + f.getId() + " | Título: " + f.getTitulo())
        );

        // Selecionar filme
        System.out.print("Digite o ID do filme: ");
        Long filmeId = input.nextLong();
        input.nextLine();

        Filme filme = filmeRepository.findById(filmeId).orElse(null);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        atua.setFilme(filme);

        System.out.println("Atuação salva com sucesso:\n" + atuaRepository.save(atua));
    }

    public void atualizar() {
        System.out.println("\n++++++ Atualizar Atuação ++++++");
        System.out.print("Digite o ID da atuação: ");
        Long id = input.nextLong();
        input.nextLine();

        Atua atua = atuaRepository.findById(id).orElse(null);
        if (atua == null) {
            System.out.println("Atuação não encontrada.");
            return;
        }

        System.out.println("Papel atual: " + atua.getPapel());
        System.out.print("Deseja alterar? (0-sim / outro-não): ");
        if (input.nextInt() == 0) {
            input.nextLine();
            System.out.print("Novo Papel: ");
            atua.setPapel(input.nextLine());
        }

        // Atualizar o filme associado (opcional)
        System.out.println("Filme atual: " + atua.getFilme().getTitulo());
        System.out.print("Deseja trocar o filme? (0-sim / outro-não): ");
        if (input.nextInt() == 0) {
            input.nextLine();
            System.out.println("Filmes disponíveis:");
            filmeRepository.findAll().forEach(f ->
                    System.out.println("ID: " + f.getId() + " | Título: " + f.getTitulo())
            );
            System.out.print("Digite o ID do novo filme: ");
            Long filmeId = input.nextLong();
            input.nextLine();
            Filme novoFilme = filmeRepository.findById(filmeId).orElse(null);
            if (novoFilme != null) {
                atua.setFilme(novoFilme);
            } else {
                System.out.println("Filme não encontrado. Mantido o anterior.");
            }
        }

        System.out.println("Atuação atualizada com sucesso:\n" + atuaRepository.save(atua));
    }

    public void listar() {
        System.out.println("\n++++++ Lista de Atuações ++++++");
        atuaRepository.findAll().forEach(System.out::println);
    }
}
