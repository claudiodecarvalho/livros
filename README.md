Projeto fullstack desenvolvido como teste técnico, utilizando Java 21 + Spring Boot 3, PostgreSQL, Angular 20, 
Docker, Flyway, Swagger, Prometheus e Grafana, com arquitetura REST e monitoramento completo.

- **Backend:** Java 21, Spring Boot 3.5.x, Spring Data JPA, Flyway (versionamento de banco), 
				PostgreSQL, Swagger / OpenAPI, Actuator + Micrometer, Prometheus, iText (relatórios PDF), Maven
- **Frontend:** Angular 20, TypeScript, HTML / CSS, HttpClient
- **Infraestrutura:** Docker, Docker Compose, Nginx (servindo frontend)
- **Observabilidade:** Spring Actuator, Prometheus, Grafana
- **Relatórios:** iText PDF

- **Estrutura do Projeto**
livros/
├── docker-compose.yml
├── livros-api/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java
├── livros-frontend/
│   └── Angular app
├── monitoring/
│   ├── prometheus.yml
│   └── grafana/
└── README.md

- **Suba toda a stack com Docker**
docker compose up --build

- Isso iniciará automaticamente:
Serviço	              URL
API Backend	          http://localhost:8080
Swagger	              http://localhost:8080/swagger-ui.html
Frontend Angular	  http://localhost:4200
Prometheus	          http://localhost:9090
Grafana	              http://localhost:3000

- **Credenciais**
-PostgreSQL
	Banco: livros
	Usuário: livros
	Senha: livros

-Grafana
	Usuário: admin
	Senha: admin (troca obrigatória no primeiro login)
	
- **Funcionalidades**
-Backend
	Cadastro de livros
	Cadastro de autores
	Cadastro de assuntos
	Relacionamento N:N entre livros e autores
	Relacionamento N:N entre livros e assuntos
	Relatório PDF (iText)
	Versionamento de banco com Flyway
	Monitoramento com Actuator + Prometheus
-Frontend
	Listagem de livros
	Cadastro e edição de livros
	Integração REST com backend
	
- **Testes Automatizados**
-O projeto possui testes unitários e de camada web utilizando:
	JUnit 5
	Mockito
	Spring Boot Test
	MockMvc

