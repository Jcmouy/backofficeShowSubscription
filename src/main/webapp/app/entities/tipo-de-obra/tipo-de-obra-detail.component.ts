import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeObra } from 'app/shared/model/tipo-de-obra.model';

@Component({
  selector: 'jhi-tipo-de-obra-detail',
  templateUrl: './tipo-de-obra-detail.component.html',
})
export class TipoDeObraDetailComponent implements OnInit {
  tipoDeObra: ITipoDeObra | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDeObra }) => (this.tipoDeObra = tipoDeObra));
  }

  previousState(): void {
    window.history.back();
  }
}
