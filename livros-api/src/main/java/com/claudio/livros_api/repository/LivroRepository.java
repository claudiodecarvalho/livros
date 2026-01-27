package com.claudio.livros_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claudio.livros_api.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
	

	    @Query("""
	        SELECT DISTINCT v FROM Livro v
	        JOIN FETCH v.livroAutores la
	        JOIN FETCH la.autor a
	        JOIN FETCH v.livroAssuntos las
	        JOIN FETCH las.assunto s
	        WHERE (:autorNome IS NULL OR LOWER(a.nome) LIKE :autorNome)
	          AND (:titulo IS NULL OR LOWER(v.titulo) LIKE :titulo)
	          AND (:assunto IS NULL OR LOWER(s.descricao) LIKE :assunto)
	    """)
	    List<Livro> buscarRelatorio(
	            @Param("titulo") String titulo,
	            @Param("autorNome") String autorNome,
	            @Param("assunto") String assunto
	    );

}