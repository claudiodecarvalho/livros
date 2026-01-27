import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Assunto } from '../models/assunto.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AssuntoService {
    private apiUrl = 'http://localhost:8080/api/assuntos';
  

  constructor(private http: HttpClient) { }

  getAssuntos(): Observable<Assunto[]> {
    return this.http.get<Assunto[]>(this.apiUrl);
  }
}
