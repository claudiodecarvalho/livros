package com.claudio.livros_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.claudio.livros_api.dto.LivroRequestDTO;
import com.claudio.livros_api.dto.LivroResponseDTO;
import com.claudio.livros_api.model.Livro;
import com.claudio.livros_api.repository.AutorRepository;
import com.claudio.livros_api.repository.AssuntoRepository;
import com.claudio.livros_api.repository.LivroAutorRepository;
import com.claudio.livros_api.repository.LivroAssuntoRepository;
import com.claudio.livros_api.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;

	@Mock
	private AutorRepository autorRepository;

	@Mock
	private AssuntoRepository assuntoRepository;

	@Mock
	private LivroAutorRepository livroAutorRepository;

	@Mock
	private LivroAssuntoRepository livroAssuntoRepository;

	@InjectMocks
	private LivroService livroService;

	@Test
	void deveListarTodosOsLivros() {
		Livro livro = new Livro();
		livro.setCodigoLivro(1);
		livro.setTitulo("Dom Casmurro");
		livro.setEditora("Globo");
		when(livroRepository.findAll()).thenReturn(List.of(livro));

		when(livroAutorRepository.findByLivro_CodigoLivro(anyInt())).thenReturn(List.of());
		when(livroAssuntoRepository.findByLivro_CodigoLivro(anyInt())).thenReturn(List.of());

		List<LivroResponseDTO> resultado = livroService.listar();

		assertThat(resultado).hasSize(1);
		assertThat(resultado.get(0).titulo()).isEqualTo("Dom Casmurro");
	}

	@Test
	void deveSalvarLivro() {
		Livro livro = new Livro();
		livro.setTitulo("DDD");
		livro.setAnoPublicacao("2021");

		Livro salvo = new Livro();
		salvo.setCodigoLivro(1);
		salvo.setTitulo("DDD");
		salvo.setAnoPublicacao("2021");

		LivroRequestDTO dto = new LivroRequestDTO(null, "DDD", "Alta Books", 1, "2021", null, null, null, null);

		when(livroRepository.save(any(Livro.class))).thenReturn(salvo);

		when(livroAutorRepository.findByLivro_CodigoLivro(anyInt())).thenReturn(List.of());
		when(livroAssuntoRepository.findByLivro_CodigoLivro(anyInt())).thenReturn(List.of());

		LivroResponseDTO resultado = livroService.criar(dto);

		assertThat(resultado.titulo()).isEqualTo("DDD");
		assertThat(resultado.codigoLivro()).isEqualTo(1);
	}
}