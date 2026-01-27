import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioLivro } from './relatorio-livro';

describe('RelatorioLivro', () => {
  let component: RelatorioLivro;
  let fixture: ComponentFixture<RelatorioLivro>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RelatorioLivro]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioLivro);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
