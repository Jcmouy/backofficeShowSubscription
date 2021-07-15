import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuncion } from 'app/shared/model/funcion.model';

@Component({
  selector: 'jhi-funcion-detail',
  templateUrl: './funcion-detail.component.html',
})
export class FuncionDetailComponent implements OnInit {
  funcion: IFuncion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcion }) => (this.funcion = funcion));
  }

  previousState(): void {
    window.history.back();
  }
}
