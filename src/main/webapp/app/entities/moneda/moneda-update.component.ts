import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMoneda, Moneda } from 'app/shared/model/moneda.model';
import { MonedaService } from './moneda.service';

@Component({
  selector: 'jhi-moneda-update',
  templateUrl: './moneda-update.component.html',
})
export class MonedaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
  });

  constructor(protected monedaService: MonedaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moneda }) => {
      this.updateForm(moneda);
    });
  }

  updateForm(moneda: IMoneda): void {
    this.editForm.patchValue({
      id: moneda.id,
      codigo: moneda.codigo,
      nombre: moneda.nombre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moneda = this.createFromForm();
    if (moneda.id !== undefined) {
      this.subscribeToSaveResponse(this.monedaService.update(moneda));
    } else {
      this.subscribeToSaveResponse(this.monedaService.create(moneda));
    }
  }

  private createFromForm(): IMoneda {
    return {
      ...new Moneda(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoneda>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
