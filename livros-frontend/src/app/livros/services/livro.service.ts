import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro } from '../models/livro.model';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  private apiUrl = 'http://localhost:8080/api/livros';
  

  constructor(private http: HttpClient) { }

  getLivros(): Observable<Livro[]> {
    return this.http.get<Livro[]>(this.apiUrl);
  }

  getLivro(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.apiUrl}/${id}`);
  }

  createLivro(livro: Livro): Observable<Livro> {
    return this.http.post<Livro>(this.apiUrl, livro);
  }

  updateLivro(id: number, livro: Livro): Observable<Livro> {
    return this.http.put<Livro>(`${this.apiUrl}/${id}`, livro);
  }

  deleteLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  gerarRelatorio(titulo?: string, autor?: string ,assunto?: string): Observable<Blob> {
    let params = new HttpParams();

    if (titulo) params = params.set('titulo', titulo);
    if (autor) params = params.set('autor', autor);
    if (assunto) params = params.set('assunto', assunto);

    // Endpoint específico do relatório por autor conforme backend
    const url = `${this.apiUrl}/relatorio-livros-autor`;

    return this.http.get(url, {
      params,
      responseType: 'blob'
    });
  }
}
