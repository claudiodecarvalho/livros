-- V2__seed_autores_assuntos.sql
-- Inicial de autores e assuntos 

BEGIN;

-- Autores
INSERT INTO autor (nome)
SELECT 'Machado de Assis'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nome = 'Machado de Assis');

INSERT INTO autor (nome)
SELECT 'Clarice Lispector'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nome = 'Clarice Lispector');

INSERT INTO autor (nome)
SELECT 'Jorge Amado'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nome = 'Jorge Amado');

INSERT INTO autor (nome)
SELECT 'Graciliano Ramos'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nome = 'Graciliano Ramos');

INSERT INTO autor (nome)
SELECT 'Carlos Drummond de Andrade'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE nome = 'Carlos Drummond de Andrade');

-- Assuntos
INSERT INTO assunto (descricao)
SELECT 'Ficção'
WHERE NOT EXISTS (SELECT 1 FROM assunto WHERE descricao = 'Ficção');

INSERT INTO assunto (descricao)
SELECT 'História'
WHERE NOT EXISTS (SELECT 1 FROM assunto WHERE descricao = 'História');

INSERT INTO assunto (descricao)
SELECT 'Programação'
WHERE NOT EXISTS (SELECT 1 FROM assunto WHERE descricao = 'Programação');

INSERT INTO assunto (descricao)
SELECT 'Ciência'
WHERE NOT EXISTS (SELECT 1 FROM assunto WHERE descricao = 'Ciência');

INSERT INTO assunto (descricao)
SELECT 'Romance'
WHERE NOT EXISTS (SELECT 1 FROM assunto WHERE descricao = 'Romance');

COMMIT;
