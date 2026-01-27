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
@Table(name = "assunto", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assunto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_assunto_gen")
    @SequenceGenerator(name = "sq_assunto_gen", sequenceName = "sq_assunto", allocationSize = 1)
    @Column(name = "codigo_assunto", nullable = false)
    private Integer codigoAssunto;

    @Column(name = "descricao", length = 20, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "assunto")
    private Set<LivroAssunto> livroAssuntos;

}