import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISubscripcion, Subscripcion } from 'app/shared/model/subscripcion.model';
import { SubscripcionService } from './subscripcion.service';
import { IPago } from 'app/shared/model/pago.model';
import { PagoService } from 'app/entities/pago/pago.service';
import { IFuncion } from 'app/shared/model/funcion.model';
import { FuncionService } from 'app/entities/funcion/funcion.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona/persona.service';

type SelectableEntity = IPago | IFuncion | IPersona;

@Component({
  selector: 'jhi-subscripcion-update',
  templateUrl: './subscripcion-update.component.html',
})
export class SubscripcionUpdateComponent implements OnInit {
  isSaving = false;
  pagos: IPago[] = [];
  funcions: IFuncion[] = [];
  personas: IPersona[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [],
    metodoPago: [],
    pagoId: [],
    funcionId: [null, Validators.required],
    personaId: [null, Validators.required],
  });

  constructor(
    protected subscripcionService: SubscripcionService,
    protected pagoService: PagoService,
    protected funcionService: FuncionService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscripcion }) => {
      if (!subscripcion.id) {
        const today = moment().startOf('day');
        subscripcion.fecha = today;
      }

      this.updateForm(subscripcion);

      this.pagoService
        .query({ 'subscripcionId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IPago[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPago[]) => {
          if (!subscripcion.pagoId) {
            this.pagos = resBody;
          } else {
            this.pagoService
              .find(subscripcion.pagoId)
              .pipe(
                map((subRes: HttpResponse<IPago>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPago[]) => (this.pagos = concatRes));
          }
        });

      this.funcionService.query().subscribe((res: HttpResponse<IFuncion[]>) => (this.funcions = res.body || []));

      this.personaService.query().subscribe((res: HttpResponse<IPersona[]>) => (this.personas = res.body || []));
    });
  }

  updateForm(subscripcion: ISubscripcion): void {
    this.editForm.patchValue({
      id: subscripcion.id,
      fecha: subscripcion.fecha ? subscripcion.fecha.format(DATE_TIME_FORMAT) : null,
      metodoPago: subscripcion.metodoPago,
      pagoId: subscripcion.pagoId,
      funcionId: subscripcion.funcionId,
      personaId: subscripcion.personaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscripcion = this.createFromForm();
    if (subscripcion.id !== undefined) {
      this.subscribeToSaveResponse(this.subscripcionService.update(subscripcion));
    } else {
      this.subscribeToSaveResponse(this.subscripcionService.create(subscripcion));
    }
  }

  private createFromForm(): ISubscripcion {
    return {
      ...new Subscripcion(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      metodoPago: this.editForm.get(['metodoPago'])!.value,
      pagoId: this.editForm.get(['pagoId'])!.value,
      funcionId: this.editForm.get(['funcionId'])!.value,
      personaId: this.editForm.get(['personaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscripcion>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
