import { Routes } from '@angular/router';
import { LivroListComponent } from './livros/pages/livro-list.component/livro-list.component';
import { LivroFormComponent } from './livros/pages/livro-form.component/livro-form.component';
import { RelatorioLivro } from './livros/pages/relatorio-livro/relatorio-livro';

export const routes: Routes = [
  { path: '', redirectTo: 'livros', pathMatch: 'full' },
  { path: 'livros', component: LivroListComponent },
  { path: 'livros/novo', component: LivroFormComponent },
  { path: 'livros/relatorio', component: RelatorioLivro },
  { path: 'livros/:id', component: LivroFormComponent },
];
