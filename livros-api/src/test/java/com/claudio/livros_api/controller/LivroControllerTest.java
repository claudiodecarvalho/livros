package com.claudio.livros_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.claudio.livros_api.dto.LivroResponseDTO;
import com.claudio.livros_api.service.LivroService;

@WebMvcTest(LivroController.class)
@AutoConfigureMockMvc(addFilters = false)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Test
    void deveRetornarListaDeLivros() throws Exception {
    	LivroResponseDTO livro = new LivroResponseDTO(
				1,
				"Clean Code",
				"Globo",
				1,
				"2015",
				null,
				null,
				Set.of(),
				Set.of()
		);
        when(livroService.listar()).thenReturn(List.of(livro));

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Clean Code"));
    }
}