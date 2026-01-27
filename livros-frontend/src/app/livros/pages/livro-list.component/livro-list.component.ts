import { Component, OnInit } from '@angular/core';
import { Livro } from '../../models/livro.model';
import { LivroService } from '../../services/livro.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-livro-list.component',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './livro-list.component.html',
  styleUrls: ['./livro-list.component.scss'],
})
export class LivroListComponent implements OnInit {

  livros: Livro[] = [];

  constructor(private livroService: LivroService) {}

  ngOnInit(): void {
    this.livroService.getLivros().subscribe({
      next: (res) => this.livros = res,
      error: (err) => console.error(err)
    });
  }

  deleteLivro(id: number) {
    if (!confirm('Deseja realmente excluir este livro?')) return;

    this.livroService.deleteLivro(id).subscribe({
      next: () => this.livros = this.livros.filter(l => l.codigoLivro !== id),
      error: (err) => console.error(err)
    });
  }
}