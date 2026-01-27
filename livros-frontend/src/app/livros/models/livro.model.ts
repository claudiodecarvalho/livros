import { Assunto } from "./assunto.model";
import { Autor } from "./autor.model";

export interface Livro {
  codigoLivro: number;
  titulo: string;
  editora: string;
  edicao: number;
  anoPublicacao: string;
  dataCadastro: string;
  valorLivro: number;
  autores: Autor[];
  assuntos: Assunto[];
}

