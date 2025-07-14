package com.capital.cinema.filme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
  }