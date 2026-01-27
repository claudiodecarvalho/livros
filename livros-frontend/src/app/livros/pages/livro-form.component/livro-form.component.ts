import { Component, OnInit } from '@angular/core';
import { Livro } from '../../models/livro.model';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LivroService } from '../../services/livro.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Autor } from '../../models/autor.model';
import { Assunto } from '../../models/assunto.model';
import { AutorService } from '../../services/autor.service';
import { AssuntoService } from '../../services/assunto.service';
import { CurrencyMaskModule } from 'ng2-currency-mask';

@Component({
  selector: 'app-livro-form.component',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CurrencyMaskModule
  ],
  templateUrl: './livro-form.component.html',
  styleUrls: ['./livro-form.component.scss'],
})
export class LivroFormComponent implements OnInit {

   livroForm!: FormGroup;
  autoresList: Autor[] = [];
  assuntosList: Assunto[] = [];

  codigoLivro?: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private livroService: LivroService,
    private router: Router,
    private autorService: AutorService,
    private assuntoService: AssuntoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.livroForm = this.fb.group({
      titulo: ['', Validators.required],
      editora: ['', Validators.required],
      edicao: [null, Validators.required],
      anoPublicacao: ['', Validators.required],
      dataCadastro: ['', Validators.required],
      valorLivro: [null, Validators.required],
      autor: this.fb.group({
        codigoAutor: [null, Validators.required],
        nome: ['']
      }),
      assunto: this.fb.group({
        codigoAssunto: [null, Validators.required],
        descricao: ['']
      })
    });

    this.autorService.getAutores().subscribe({
      next: list => this.autoresList = list,
      error: err => console.error('Erro ao carregar autores', err)
    });

    this.assuntoService.getAssuntos().subscribe({
      next: list => this.assuntosList = list,
      error: err => console.error('Erro ao carregar assuntos', err)
    });

    // Detecta modo edição
    this.route.paramMap.subscribe(params => {
      const codigo = params.get('id');
      console.log('Código do livro:', codigo);
      console.log('Parâmetros da rota:', params);
      if (codigo && codigo !== 'novo') {
        this.isEditMode = true;
        this.codigoLivro = +codigo;
        this.livroService.getLivro(this.codigoLivro).subscribe({
          next: livro => this.preencherFormulario(livro),
          error: err => console.error('Erro ao carregar livro', err)
        });
      }
    });
  }

  preencherFormulario(livro: Livro) {
    this.livroForm.patchValue({
      titulo: livro.titulo,
      editora: livro.editora,
      edicao: livro.edicao,
      anoPublicacao: livro.anoPublicacao,
      dataCadastro: livro.dataCadastro,
      valorLivro: livro.valorLivro,
      autor: livro.autores && livro.autores.length > 0 ? {
        codigoAutor: livro.autores[0].codigoAutor,
        nome: livro.autores[0].nome
      } : { codigoAutor: null, nome: '' },
      assunto: livro.assuntos && livro.assuntos.length > 0 ? {
        codigoAssunto: livro.assuntos[0].codigoAssunto,
        descricao: livro.assuntos[0].descricao
      } : { codigoAssunto: null, descricao: '' }
    });
  }

  get autor(): FormGroup {
    return this.livroForm.get('autor') as FormGroup;
  }

  get assunto(): FormGroup {
    return this.livroForm.get('assunto') as FormGroup;
  }

  onAutorSelect(event: Event) {
    const select = event.target as HTMLSelectElement | null;
    const value = select?.value ?? '';
    const cod = value === '' ? null : +value;
    if (cod === null || Number.isNaN(cod)) {
      this.autor?.patchValue({ codigoAutor: null, nome: '' });
      return;
    }
    const autor = this.autoresList.find(a => a.codigoAutor === cod);
    if (autor) {
      this.autor?.patchValue({ codigoAutor: autor.codigoAutor, nome: autor.nome });
    } else {
      this.autor?.patchValue({ codigoAutor: null, nome: '' });
    }
  }

  onAssuntoSelect(event: Event) {
    const select = event.target as HTMLSelectElement | null;
    const value = select?.value ?? '';
    const cod = value === '' ? null : +value;
    if (cod === null || Number.isNaN(cod)) {
      this.assunto?.patchValue({ codigoAssunto: null, descricao: '' });
      return;
    }
    const assunto = this.assuntosList.find(a => a.codigoAssunto === cod);
    if (assunto) {
      this.assunto?.patchValue({ codigoAssunto: assunto.codigoAssunto, descricao: assunto.descricao });
    } else {
      this.assunto?.patchValue({ codigoAssunto: null, descricao: '' });
    }
  }



  submit() {
    if (this.livroForm.invalid) {
      this.livroForm.markAllAsTouched();
      return;
    }

    const formValue = this.livroForm.value;
    const livro: Livro = {
      ...formValue,
      autores: formValue.autor ? [formValue.autor] : [],
      assuntos: formValue.assunto ? [formValue.assunto] : []
    };

    if (this.isEditMode && this.codigoLivro) {
      this.livroService.updateLivro(this.codigoLivro, livro).subscribe({
        next: () => this.router.navigate(['/livros']),
        error: err => console.error(err)
      });
    } else {
      this.livroService.createLivro(livro).subscribe({
        next: () => this.router.navigate(['/livros']),
        error: err => console.error(err)
      });
    }
  }
}