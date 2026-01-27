package com.claudio.livros_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudio.livros_api.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
}
