import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoDeObra, TipoDeObra } from 'app/shared/model/tipo-de-obra.model';
import { TipoDeObraService } from './tipo-de-obra.service';

@Component({
  selector: 'jhi-tipo-de-obra-update',
  templateUrl: './tipo-de-obra-update.component.html',
})
export class TipoDeObraUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    descripcion: [],
  });

  constructor(protected tipoDeObraService: TipoDeObraService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDeObra }) => {
      this.updateForm(tipoDeObra);
    });
  }

  updateForm(tipoDeObra: ITipoDeObra): void {
    this.editForm.patchValue({
      id: tipoDeObra.id,
      tipo: tipoDeObra.tipo,
      descripcion: tipoDeObra.descripcion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoDeObra = this.createFromForm();
    if (tipoDeObra.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoDeObraService.update(tipoDeObra));
    } else {
      this.subscribeToSaveResponse(this.tipoDeObraService.create(tipoDeObra));
    }
  }

  private createFromForm(): ITipoDeObra {
    return {
      ...new TipoDeObra(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeObra>>): void {
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
