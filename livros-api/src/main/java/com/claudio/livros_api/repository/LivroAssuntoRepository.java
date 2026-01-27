package com.claudio.livros_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudio.livros_api.model.LivroAssunto;

import java.util.List;

@Repository
public interface LivroAssuntoRepository extends JpaRepository<LivroAssunto, Integer> {
    List<LivroAssunto> findByLivro_CodigoLivro(Integer codigoLivro);

    // delete em lote por codigo do livro
    void deleteByLivro_CodigoLivro(Integer codigoLivro);

    // delete em lote por livro e lista de assuntos
    void deleteByLivro_CodigoLivroAndAssunto_CodigoAssuntoIn(Integer codigoLivro, java.util.Set<Integer> assuntoIds);
}