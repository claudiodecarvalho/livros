package com.claudio.livros_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudio.livros_api.model.LivroAutor;

import java.util.List;

@Repository
public interface LivroAutorRepository extends JpaRepository<LivroAutor, Integer> {
    List<LivroAutor> findByLivro_CodigoLivro(Integer codigoLivro);

    void deleteByLivro_CodigoLivro(Integer codigoLivro);

    void deleteByLivro_CodigoLivroAndAutor_CodigoAutorIn(Integer codigoLivro, java.util.Set<Integer> autorIds);
}