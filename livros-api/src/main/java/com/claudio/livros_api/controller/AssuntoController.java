package com.claudio.livros_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claudio.livros_api.dto.AssuntoDTO;
import com.claudio.livros_api.service.AssuntoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assuntos")
@RequiredArgsConstructor
public class AssuntoController {
	private final AssuntoService assuntoService;


    @GetMapping
    public ResponseEntity<List<AssuntoDTO>> listar() {
        return ResponseEntity.ok(assuntoService.listar());
    }

}