import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Livro } from '../../models/livro.model';
import { LivroService } from '../../services/livro.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-relatorio-livro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './relatorio-livro.html',
  styleUrls: ['./relatorio-livro.scss'],
})
export class RelatorioLivro {
  form!: FormGroup;

  loading = false;

  constructor(
    private fb: FormBuilder,
    private service: LivroService
  ) {
    this.form = this.fb.group({
      titulo: [''],
      autor: [''],
      assunto: ['']
    });
  }

  gerar() {
    this.loading = true;

    const { titulo, autor, assunto } = this.form.value;

    this.service.gerarRelatorio(titulo!, autor!, assunto!).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'relatorio-livros.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }
}
