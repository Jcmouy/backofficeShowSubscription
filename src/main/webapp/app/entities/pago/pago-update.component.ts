import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPago, Pago } from 'app/shared/model/pago.model';
import { PagoService } from './pago.service';

@Component({
  selector: 'jhi-pago-update',
  templateUrl: './pago-update.component.html',
})
export class PagoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idExterno: [],
    fechaExterna: [],
  });

  constructor(protected pagoService: PagoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pago }) => {
      if (!pago.id) {
        const today = moment().startOf('day');
        pago.fechaExterna = today;
      }

      this.updateForm(pago);
    });
  }

  updateForm(pago: IPago): void {
    this.editForm.patchValue({
      id: pago.id,
      idExterno: pago.idExterno,
      fechaExterna: pago.fechaExterna ? pago.fechaExterna.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pago = this.createFromForm();
    if (pago.id !== undefined) {
      this.subscribeToSaveResponse(this.pagoService.update(pago));
    } else {
      this.subscribeToSaveResponse(this.pagoService.create(pago));
    }
  }

  private createFromForm(): IPago {
    return {
      ...new Pago(),
      id: this.editForm.get(['id'])!.value,
      idExterno: this.editForm.get(['idExterno'])!.value,
      fechaExterna: this.editForm.get(['fechaExterna'])!.value
        ? moment(this.editForm.get(['fechaExterna'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPago>>): void {
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
