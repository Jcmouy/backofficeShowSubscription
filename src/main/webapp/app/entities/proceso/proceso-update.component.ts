import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProceso, Proceso } from 'app/shared/model/proceso.model';
import { ProcesoService } from './proceso.service';

@Component({
  selector: 'jhi-proceso-update',
  templateUrl: './proceso-update.component.html',
})
export class ProcesoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
  });

  constructor(protected procesoService: ProcesoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proceso }) => {
      if (!proceso.id) {
        const today = moment().startOf('day');
        proceso.fecha = today;
      }

      this.updateForm(proceso);
    });
  }

  updateForm(proceso: IProceso): void {
    this.editForm.patchValue({
      id: proceso.id,
      tipo: proceso.tipo,
      fecha: proceso.fecha ? proceso.fecha.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proceso = this.createFromForm();
    if (proceso.id !== undefined) {
      this.subscribeToSaveResponse(this.procesoService.update(proceso));
    } else {
      this.subscribeToSaveResponse(this.procesoService.create(proceso));
    }
  }

  private createFromForm(): IProceso {
    return {
      ...new Proceso(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProceso>>): void {
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
