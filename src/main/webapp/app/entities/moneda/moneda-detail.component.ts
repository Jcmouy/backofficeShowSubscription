import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoneda } from 'app/shared/model/moneda.model';

@Component({
  selector: 'jhi-moneda-detail',
  templateUrl: './moneda-detail.component.html',
})
export class MonedaDetailComponent implements OnInit {
  moneda: IMoneda | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moneda }) => (this.moneda = moneda));
  }

  previousState(): void {
    window.history.back();
  }
}
