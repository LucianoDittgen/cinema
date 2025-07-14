package com.capital.cinema.atua;

import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AtuaController {

    private final AtuaRepository repository;
    private final Scanner input = new Scanner(System.in);

    public AtuaController(AtuaRepository repository) {
        this.repository = repository;
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


        System.out.println("Atuação salva com sucesso:\n" + repository.save(atua));
    }

    public void atualizar() {
        System.out.println("\n++++++ Atualizar Atuação ++++++");
        System.out.print("Digite o ID da atuação: ");
        Long id = input.nextLong();
        input.nextLine();

        Atua atua = repository.findById(id).orElse(null);
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


        System.out.println("Atuação atualizada com sucesso:\n" + repository.save(atua));
    }

    public void listar() {
        System.out.println("\n++++++ Lista de Atuações ++++++");
        repository.findAll().forEach(System.out::println);
    }
}
