CREATE SEQUENCE IF NOT EXISTS sq_autor;

CREATE TABLE autor (
    codigo_autor INTEGER NOT NULL DEFAULT nextval('sq_autor'),
    nome VARCHAR(40) NOT NULL,
    CONSTRAINT pk_autor PRIMARY KEY (codigo_autor)
);


CREATE SEQUENCE IF NOT EXISTS sq_livro;

CREATE TABLE livro (
    codigo_livro INTEGER NOT NULL DEFAULT nextval('sq_livro'),
    titulo VARCHAR(40) NOT NULL,
    editora VARCHAR(40) NOT NULL,
    edicao INTEGER NOT NULL,
    ano_publicacao VARCHAR(4) NOT NULL,
    data_cadastro Date NOT NULL,
    valor_livro numeric(12,2) NULL,
    CONSTRAINT pk_livro PRIMARY KEY (codigo_livro)
);


CREATE SEQUENCE IF NOT EXISTS sq_assunto;

CREATE TABLE assunto (
    codigo_assunto INTEGER NOT NULL DEFAULT nextval('sq_assunto'),
    descricao VARCHAR(20) NOT NULL,
    CONSTRAINT pk_assunto PRIMARY KEY (codigo_assunto)
);


CREATE SEQUENCE IF NOT EXISTS sq_livro_autor;

CREATE TABLE livro_autor (
    codigo_livro_autor INTEGER NOT NULL DEFAULT nextval('sq_livro_autor'),
    codigo_livro INTEGER NOT NULL,
    codigo_autor INTEGER NOT NULL,
    CONSTRAINT pk_livro_autor PRIMARY KEY (codigo_livro_autor),
    CONSTRAINT fk_livro_autor_autor FOREIGN KEY (codigo_autor) REFERENCES autor (codigo_autor),
    CONSTRAINT fk_livro_autor_livro FOREIGN KEY (codigo_livro) REFERENCES livro (codigo_livro)
);


CREATE SEQUENCE IF NOT EXISTS sq_livro_assunto;

CREATE TABLE livro_assunto (
    codigo_livro_assunto INTEGER NOT NULL DEFAULT nextval('sq_livro_assunto'),
    codigo_livro INTEGER NOT NULL,
    codigo_assunto INTEGER NOT NULL,
    CONSTRAINT pk_livro_assunto PRIMARY KEY (codigo_livro_assunto),
    CONSTRAINT fk_livro_assunto_assunto FOREIGN KEY (codigo_assunto) REFERENCES assunto (codigo_assunto),
    CONSTRAINT fk_livro_assunto_livro FOREIGN KEY (codigo_livro) REFERENCES livro (codigo_livro)
);

-- Unique index for livro_autor (codigo_livro, codigo_autor)
CREATE UNIQUE INDEX IF NOT EXISTS idx_livro_autor_unq ON livro_autor (codigo_livro, codigo_autor);

-- Unique index for livro_assunto (codigo_livro, codigo_assunto)
CREATE UNIQUE INDEX IF NOT EXISTS idx_livro_assunto_unq ON livro_assunto (codigo_livro, codigo_assunto);