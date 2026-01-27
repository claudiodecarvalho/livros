package com.claudio.livros_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudio.livros_api.dto.AutorDTO;
import com.claudio.livros_api.model.Autor;
import com.claudio.livros_api.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AutorService {
    private final AutorRepository autorRepository;
    
    @Transactional(readOnly = true)
    public List<AutorDTO> listar() {
        return autorRepository.findAll()
                .stream()
                .map(this::autorToDTO)
                .collect(Collectors.toList());
    }
    
    private AutorDTO autorToDTO(Autor autor) {
		return new AutorDTO(
				autor.getCodigoAutor(),
				autor.getNome()
		);
	}
}
