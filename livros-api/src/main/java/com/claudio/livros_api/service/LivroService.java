package com.claudio.livros_api.service;

import com.claudio.livros.exception.*;
import com.claudio.livros_api.dto.AssuntoDTO;
import com.claudio.livros_api.dto.AutorDTO;
import com.claudio.livros_api.dto.LivroRequestDTO;
import com.claudio.livros_api.dto.LivroResponseDTO;
import com.claudio.livros_api.model.Livro;
import com.claudio.livros_api.model.LivroAssunto;
import com.claudio.livros_api.model.LivroAutor;
import com.claudio.livros_api.repository.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroAutorRepository livroAutorRepository;
    private final LivroAssuntoRepository livroAssuntoRepository;

    public LivroResponseDTO criar(LivroRequestDTO dto) {
        Livro livro = new Livro();
        aplicarDTO(livro, dto);
        Livro salvo = livroRepository.save(livro);

        // persistir associações se fornecidas
        if (dto.autores() != null) {
            syncAutores(salvo, dto.autores());
        }
        if (dto.assuntos() != null) {
            syncAssuntos(salvo, dto.assuntos());
        }

        return toResponseDTO(salvo);
    }

    public LivroResponseDTO atualizar(Integer id, LivroRequestDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        aplicarDTO(livro, dto);
        Livro salvo = livroRepository.save(livro);

        // sincroniza associações: se campo for null, mantém; se vazio limpa; se com itens sincroniza
        if (dto.autores() != null) {
            syncAutores(salvo, dto.autores());
        }
        if (dto.assuntos() != null) {
            syncAssuntos(salvo, dto.assuntos());
        }

        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public LivroResponseDTO buscarPorId(Integer id) {
        return livroRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDTO> listar() {
        return livroRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Integer id) {
    	var livro = livroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
     
    	livroAutorRepository.deleteByLivro_CodigoLivro(livro.getCodigoLivro());
    	livroAssuntoRepository.deleteByLivro_CodigoLivro(livro.getCodigoLivro());
    	
        livroRepository.deleteById(id);
    }

    private void aplicarDTO(Livro livro, LivroRequestDTO dto) {
        // popula campos simples
        livro.setTitulo(dto.titulo());
        livro.setEditora(dto.editora());
        livro.setEdicao(dto.edicao());
        livro.setAnoPublicacao(dto.anoPublicacao());
        // data de cadastro: utiliza valor do DTO se fornecido, caso contrário conserva existente ou define agora
        if (dto.dataCadastro() != null) {
            livro.setDataCadastro(dto.dataCadastro());
        } else if (livro.getDataCadastro() == null) {
            livro.setDataCadastro(LocalDate.now());
        }

        // valor do livro: utiliza valor do DTO se fornecido, caso contrário conserva existente ou define ZERO
        if (dto.valorLivro() != null) {
            livro.setValorLivro(dto.valorLivro());
        } else if (livro.getValorLivro() == null) {
            livro.setValorLivro(BigDecimal.ZERO);
        }
    }

    private void syncAutores(Livro livro, Set<AutorDTO> autorDTOs) {
        Integer codigoLivro = livro.getCodigoLivro();
        // ids desejados
        Set<Integer> desejados = autorDTOs.stream()
                .map(AutorDTO::codigoAutor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // valida existencia dos autores desejados
        if (!desejados.isEmpty()) {
            var encontrados = autorRepository.findAllById(desejados);
            if (encontrados.size() != desejados.size()) {
                throw new ResourceNotFoundException("Um ou mais autores não foram encontrados");
            }
        }

        // existentes
        List<LivroAutor> existentes = livroAutorRepository.findByLivro_CodigoLivro(codigoLivro);
        Set<Integer> existentesIds = existentes.stream()
                .map(la -> la.getAutor().getCodigoAutor())
                .collect(Collectors.toSet());

        // remover os que não estão mais desejados
        List<LivroAutor> toRemove = existentes.stream()
                .filter(la -> !desejados.contains(la.getAutor().getCodigoAutor()))
                .collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            livroAutorRepository.deleteAll(toRemove);
        }

        // adicionar novos
        Set<Integer> toAdd = desejados.stream()
                .filter(id -> !existentesIds.contains(id))
                .collect(Collectors.toSet());
        if (!toAdd.isEmpty()) {
            var autores = autorRepository.findAllById(toAdd);
            var novas = autores.stream()
                    .map(a -> LivroAutor.builder().livro(livro).autor(a).build())
                    .collect(Collectors.toList());
            livroAutorRepository.saveAll(novas);
        }
    }

    private void syncAssuntos(Livro livro, Set<AssuntoDTO> assuntoDTOs) {
        Integer codigoLivro = livro.getCodigoLivro();
        Set<Integer> desejados = assuntoDTOs.stream()
                .map(AssuntoDTO::codigoAssunto)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!desejados.isEmpty()) {
            var encontrados = assuntoRepository.findAllById(desejados);
            if (encontrados.size() != desejados.size()) {
                throw new ResourceNotFoundException("Um ou mais assuntos não foram encontrados");
            }
        }

        List<LivroAssunto> existentes = livroAssuntoRepository.findByLivro_CodigoLivro(codigoLivro);
        Set<Integer> existentesIds = existentes.stream()
                .map(la -> la.getAssunto().getCodigoAssunto())
                .collect(Collectors.toSet());

        List<LivroAssunto> toRemove = existentes.stream()
                .filter(la -> !desejados.contains(la.getAssunto().getCodigoAssunto()))
                .collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            livroAssuntoRepository.deleteAll(toRemove);
        }

        Set<Integer> toAdd = desejados.stream()
                .filter(id -> !existentesIds.contains(id))
                .collect(Collectors.toSet());
        if (!toAdd.isEmpty()) {
            var assuntos = assuntoRepository.findAllById(toAdd);
            var novas = assuntos.stream()
                    .map(s -> LivroAssunto.builder().livro(livro).assunto(s).build())
                    .collect(Collectors.toList());
            livroAssuntoRepository.saveAll(novas);
        }
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
        Set<AutorDTO> autores = Set.of();
        Set<AssuntoDTO> assuntos = Set.of();

        if (livro.getCodigoLivro() != null) {
            var autoresRelacionados = livroAutorRepository.findByLivro_CodigoLivro(livro.getCodigoLivro());
            autores = autoresRelacionados.stream()
                    .map(LivroAutor::getAutor)
                    .filter(Objects::nonNull)
                    .map(a -> new AutorDTO(a.getCodigoAutor(), a.getNome()))
                    .collect(Collectors.toSet());

            var assuntosRelacionados = livroAssuntoRepository.findByLivro_CodigoLivro(livro.getCodigoLivro());
            assuntos = assuntosRelacionados.stream()
                    .map(LivroAssunto::getAssunto)
                    .filter(Objects::nonNull)
                    .map(s -> new AssuntoDTO(s.getCodigoAssunto(), s.getDescricao()))
                    .collect(Collectors.toSet());
        }

        return new LivroResponseDTO(
        		livro.getCodigoLivro(),
                livro.getTitulo(),
                livro.getEditora(),
                livro.getEdicao(),
                livro.getAnoPublicacao(),
                livro.getDataCadastro(),
                livro.getValorLivro(),
                autores,
                assuntos
        );
    }
    

    public byte[] gerarPdf(String titulo, String autorNome, String assunto) throws IOException {

        String filtroTitulo = (titulo == null || titulo.isBlank()) ? null : "%" + titulo.toLowerCase() + "%";
        String filtroAutor = (autorNome == null || autorNome.isBlank()) ? null : "%" + autorNome.toLowerCase() + "%";
        String filtroAssunto = (assunto == null || assunto.isBlank()) ? null : "%" + assunto.toLowerCase() + "%";

        List<Livro> dados =
                livroRepository.buscarRelatorio(filtroTitulo, filtroAutor, filtroAssunto);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Relatório de Livros")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph(" "));

        Table table = new Table(5);
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell("Titulo");
        table.addHeaderCell("Editora");
        table.addHeaderCell("Autores");
        table.addHeaderCell("Assuntos");
        table.addHeaderCell("Publicação");

        for (Livro livro : dados) {
            table.addCell(livro.getTitulo());
            table.addCell(livro.getEditora());
            table.addCell(livro.getLivroAutores().stream()
                    .map(la -> la.getAutor().getNome())
                    .collect(Collectors.joining(", "))
            );
            table.addCell(livro.getLivroAssuntos().stream().map(ls -> ls.getAssunto().getDescricao()).collect(Collectors.joining(", ")));
            table.addCell(
                    livro.getDataCadastro() != null
                            ? livro.getDataCadastro().toString()
                            : "-"
            );
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
