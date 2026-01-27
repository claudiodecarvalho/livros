import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { LivrosRoutingModule } from './livros-routing-module';
import { LivroListComponent } from './pages/livro-list.component/livro-list.component';
import { LivroFormComponent } from './pages/livro-form.component/livro-form.component';
import { RouterModule } from '@angular/router';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LivrosRoutingModule,
     RouterModule,
    LivroListComponent,  
    LivroFormComponent
  ],
  exports: [
    LivroListComponent,
    LivroFormComponent
  ]
})
export class LivrosModule { }
