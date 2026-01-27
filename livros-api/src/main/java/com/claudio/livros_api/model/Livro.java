package com.claudio.livros_api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livro", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_livro_gen")
    @SequenceGenerator(name = "sq_livro_gen", sequenceName = "sq_livro", allocationSize = 1)
    @Column(name = "codigo_livro", nullable = false)
    private Integer codigoLivro;

    @Column(name = "titulo", length = 40, nullable = false)
    private String titulo;

    @Column(name = "editora", length = 40, nullable = false)
    private String editora;

    @Column(name = "edicao", nullable = false)
    private Integer edicao;

    @Column(name = "ano_publicacao", length = 4, nullable = false)
    private String anoPublicacao;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "valor_livro", precision = 12, scale = 2, nullable = false)
    private BigDecimal valorLivro;

    @OneToMany(mappedBy = "livro")
    private Set<LivroAssunto> livroAssuntos;
    
    @OneToMany(mappedBy = "livro")
    private Set<LivroAutor> livroAutores;

}