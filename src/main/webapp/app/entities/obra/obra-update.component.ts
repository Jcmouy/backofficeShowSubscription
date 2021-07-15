import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IObra, Obra } from 'app/shared/model/obra.model';
import { ObraService } from './obra.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEtiqueta } from 'app/shared/model/etiqueta.model';
import { EtiquetaService } from 'app/entities/etiqueta/etiqueta.service';
import { ITipoDeObra } from 'app/shared/model/tipo-de-obra.model';
import { TipoDeObraService } from 'app/entities/tipo-de-obra/tipo-de-obra.service';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta/cuenta.service';

type SelectableEntity = IEtiqueta | ITipoDeObra | ICuenta;

@Component({
  selector: 'jhi-obra-update',
  templateUrl: './obra-update.component.html',
})
export class ObraUpdateComponent implements OnInit {
  isSaving = false;
  etiquetas: IEtiqueta[] = [];
  tipodeobras: ITipoDeObra[] = [];
  cuentas: ICuenta[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [],
    imagen: [null, [Validators.required]],
    imagenContentType: [],
    icono: [null, [Validators.required]],
    iconoContentType: [],
    protagonistas: [],
    direccion: [],
    autores: [],
    fecha: [null, [Validators.required]],
    duracion: [],
    etiquetas: [],
    tipoId: [null, Validators.required],
    cuentaId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected obraService: ObraService,
    protected etiquetaService: EtiquetaService,
    protected tipoDeObraService: TipoDeObraService,
    protected cuentaService: CuentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obra }) => {
      this.updateForm(obra);

      this.etiquetaService.query().subscribe((res: HttpResponse<IEtiqueta[]>) => (this.etiquetas = res.body || []));

      this.tipoDeObraService.query().subscribe((res: HttpResponse<ITipoDeObra[]>) => (this.tipodeobras = res.body || []));

      this.cuentaService.query().subscribe((res: HttpResponse<ICuenta[]>) => (this.cuentas = res.body || []));
    });
  }

  updateForm(obra: IObra): void {
    this.editForm.patchValue({
      id: obra.id,
      nombre: obra.nombre,
      descripcion: obra.descripcion,
      imagen: obra.imagen,
      imagenContentType: obra.imagenContentType,
      icono: obra.icono,
      iconoContentType: obra.iconoContentType,
      protagonistas: obra.protagonistas,
      direccion: obra.direccion,
      autores: obra.autores,
      fecha: obra.fecha,
      duracion: obra.duracion,
      etiquetas: obra.etiquetas,
      tipoId: obra.tipoId,
      cuentaId: obra.cuentaId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('plataformaBackofficeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obra = this.createFromForm();
    if (obra.id !== undefined) {
      this.subscribeToSaveResponse(this.obraService.update(obra));
    } else {
      this.subscribeToSaveResponse(this.obraService.create(obra));
    }
  }

  private createFromForm(): IObra {
    return {
      ...new Obra(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      iconoContentType: this.editForm.get(['iconoContentType'])!.value,
      icono: this.editForm.get(['icono'])!.value,
      protagonistas: this.editForm.get(['protagonistas'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      autores: this.editForm.get(['autores'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      duracion: this.editForm.get(['duracion'])!.value,
      etiquetas: this.editForm.get(['etiquetas'])!.value,
      tipoId: this.editForm.get(['tipoId'])!.value,
      cuentaId: this.editForm.get(['cuentaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObra>>): void {
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

  getSelected(selectedVals: IEtiqueta[], option: IEtiqueta): IEtiqueta {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
