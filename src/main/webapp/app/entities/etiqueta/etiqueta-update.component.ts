import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtiqueta, Etiqueta } from 'app/shared/model/etiqueta.model';
import { EtiquetaService } from './etiqueta.service';

@Component({
  selector: 'jhi-etiqueta-update',
  templateUrl: './etiqueta-update.component.html',
})
export class EtiquetaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
  });

  constructor(protected etiquetaService: EtiquetaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etiqueta }) => {
      this.updateForm(etiqueta);
    });
  }

  updateForm(etiqueta: IEtiqueta): void {
    this.editForm.patchValue({
      id: etiqueta.id,
      nombre: etiqueta.nombre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etiqueta = this.createFromForm();
    if (etiqueta.id !== undefined) {
      this.subscribeToSaveResponse(this.etiquetaService.update(etiqueta));
    } else {
      this.subscribeToSaveResponse(this.etiquetaService.create(etiqueta));
    }
  }

  private createFromForm(): IEtiqueta {
    return {
      ...new Etiqueta(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtiqueta>>): void {
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
