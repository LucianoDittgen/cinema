package com.capital.cinema.atua;

import com.capital.cinema.filme.Filme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Atua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String papel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;


}
