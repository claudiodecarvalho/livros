package com.claudio.livros_api.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livro_autor", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroAutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_livro_autor_gen")
    @SequenceGenerator(name = "sq_livro_autor_gen", sequenceName = "sq_livro_autor", allocationSize = 1)
    @Column(name = "codigo_livro_autor", nullable = false)
    private Integer codigoLivroAutor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_livro", referencedColumnName = "codigo_livro", nullable = false)
    private Livro livro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_autor", referencedColumnName = "codigo_autor", nullable = false)
    private Autor autor;

}
