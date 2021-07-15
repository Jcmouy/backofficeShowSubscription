import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';
import { IObra } from 'app/shared/model/obra.model';
import { ObraService } from 'app/entities/obra/obra.service';

@Component({
  selector: 'jhi-contenido-update',
  templateUrl: './contenido-update.component.html',
})
export class ContenidoUpdateComponent implements OnInit {
  isSaving = false;
  obras: IObra[] = [];

  editForm = this.fb.group({
    id: [],
    indice: [null, [Validators.required]],
    subindice: [],
    tipoContenido: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    resumen: [],
    obraId: [null, Validators.required],
  });

  constructor(
    protected contenidoService: ContenidoService,
    protected obraService: ObraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenido }) => {
      this.updateForm(contenido);

      this.obraService.query().subscribe((res: HttpResponse<IObra[]>) => (this.obras = res.body || []));
    });
  }

  updateForm(contenido: IContenido): void {
    this.editForm.patchValue({
      id: contenido.id,
      indice: contenido.indice,
      subindice: contenido.subindice,
      tipoContenido: contenido.tipoContenido,
      valor: contenido.valor,
      resumen: contenido.resumen,
      obraId: contenido.obraId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contenido = this.createFromForm();
    if (contenido.id !== undefined) {
      this.subscribeToSaveResponse(this.contenidoService.update(contenido));
    } else {
      this.subscribeToSaveResponse(this.contenidoService.create(contenido));
    }
  }

  private createFromForm(): IContenido {
    return {
      ...new Contenido(),
      id: this.editForm.get(['id'])!.value,
      indice: this.editForm.get(['indice'])!.value,
      subindice: this.editForm.get(['subindice'])!.value,
      tipoContenido: this.editForm.get(['tipoContenido'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      resumen: this.editForm.get(['resumen'])!.value,
      obraId: this.editForm.get(['obraId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenido>>): void {
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

  trackById(index: number, item: IObra): any {
    return item.id;
  }
}
