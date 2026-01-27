package com.claudio.livros_api.model;

import java.io.Serializable;
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
@Table(name = "autor", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_autor_gen")
    @SequenceGenerator(name = "sq_autor_gen", sequenceName = "sq_autor", allocationSize = 1)
    @Column(name = "codigo_autor", nullable = false)
    private Integer codigoAutor;

    @Column(name = "nome", length = 40, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "autor")
    private Set<LivroAutor> livroAutores;

}