package com.claudio.livros_api.dto;

import java.util.Set;
import java.time.LocalDate;
import java.math.BigDecimal;

public record LivroResponseDTO(
		Integer codigoLivro,
		
		String titulo,

		String editora,

		Integer edicao,

		String anoPublicacao,

		LocalDate dataCadastro,

		BigDecimal valorLivro,

		Set<AutorDTO> autores,

		Set<AssuntoDTO> assuntos) {
}