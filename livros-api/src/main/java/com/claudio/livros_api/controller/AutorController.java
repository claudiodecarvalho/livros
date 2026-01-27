package com.claudio.livros_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claudio.livros_api.dto.AutorDTO;
import com.claudio.livros_api.service.AutorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorController {
	private final AutorService autorService;


    @GetMapping
    public ResponseEntity<List<AutorDTO>> listar() {
        return ResponseEntity.ok(autorService.listar());
    }

}