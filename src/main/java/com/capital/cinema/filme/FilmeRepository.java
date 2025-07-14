package com.capital.cinema.filme;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    Filme getFilmeById(long id);

    List<Filme> getFilmesByTitulo(String s);
}