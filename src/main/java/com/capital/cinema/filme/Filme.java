package com.capital.cinema.filme;

import com.capital.cinema.atua.Atua;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import java.time.Duration;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Duration duracao;

    @OneToMany(mappedBy = "filme", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Atua> atuacoes;
}
