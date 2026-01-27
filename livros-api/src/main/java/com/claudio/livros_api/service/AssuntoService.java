package com.claudio.livros_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudio.livros_api.dto.AssuntoDTO;
import com.claudio.livros_api.model.Assunto;
import com.claudio.livros_api.repository.AssuntoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AssuntoService {
	
    private final AssuntoRepository assuntoRepository;

    @Transactional(readOnly = true)
    public List<AssuntoDTO> listar() {
        return assuntoRepository.findAll()
                .stream()
                .map(this::assuntoToDTO)
                .collect(Collectors.toList());
    }
    
    private AssuntoDTO assuntoToDTO(Assunto assunto) {
		return new AssuntoDTO(
				assunto.getCodigoAssunto(),
				assunto.getDescricao()
		);
	}
}
