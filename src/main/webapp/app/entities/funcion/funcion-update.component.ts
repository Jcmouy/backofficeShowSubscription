import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFuncion, Funcion } from 'app/shared/model/funcion.model';
import { FuncionService } from './funcion.service';
import { IObra } from 'app/shared/model/obra.model';
import { ObraService } from 'app/entities/obra/obra.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';
import { IMoneda } from 'app/shared/model/moneda.model';
import { MonedaService } from 'app/entities/moneda/moneda.service';

type SelectableEntity = IObra | IPais | IMoneda;

@Component({
  selector: 'jhi-funcion-update',
  templateUrl: './funcion-update.component.html',
})
export class FuncionUpdateComponent implements OnInit {
  isSaving = false;
  obras: IObra[] = [];
  pais: IPais[] = [];
  monedas: IMoneda[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    precio: [null, [Validators.required]],
    obraId: [null, Validators.required],
    paisId: [null, Validators.required],
    monedaId: [null, Validators.required],
  });

  constructor(
    protected funcionService: FuncionService,
    protected obraService: ObraService,
    protected paisService: PaisService,
    protected monedaService: MonedaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcion }) => {
      this.updateForm(funcion);

      this.obraService.query().subscribe((res: HttpResponse<IObra[]>) => (this.obras = res.body || []));

      this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body || []));

      this.monedaService.query().subscribe((res: HttpResponse<IMoneda[]>) => (this.monedas = res.body || []));
    });
  }

  updateForm(funcion: IFuncion): void {
    this.editForm.patchValue({
      id: funcion.id,
      fecha: funcion.fecha,
      precio: funcion.precio,
      obraId: funcion.obraId,
      paisId: funcion.paisId,
      monedaId: funcion.monedaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcion = this.createFromForm();
    if (funcion.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionService.update(funcion));
    } else {
      this.subscribeToSaveResponse(this.funcionService.create(funcion));
    }
  }

  private createFromForm(): IFuncion {
    return {
      ...new Funcion(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      precio: this.editForm.get(['precio'])!.value,
      obraId: this.editForm.get(['obraId'])!.value,
      paisId: this.editForm.get(['paisId'])!.value,
      monedaId: this.editForm.get(['monedaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncion>>): void {
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
