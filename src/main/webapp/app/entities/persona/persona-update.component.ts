import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersona, Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta/cuenta.service';

type SelectableEntity = IUser | IPais | ICuenta;

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html',
})
export class PersonaUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  pais: IPais[] = [];
  cuentas: ICuenta[] = [];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    codigo: [],
    nombres: [null, [Validators.required]],
    apellidos: [null, [Validators.required]],
    fechaNacimiento: [],
    correoElectronico: [],
    telefono: [],
    usuarioId: [],
    paisId: [null, Validators.required],
    cuentaId: [null, Validators.required],
  });

  constructor(
    protected personaService: PersonaService,
    protected userService: UserService,
    protected paisService: PaisService,
    protected cuentaService: CuentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body || []));

      this.cuentaService.query().subscribe((res: HttpResponse<ICuenta[]>) => (this.cuentas = res.body || []));
    });
  }

  updateForm(persona: IPersona): void {
    this.editForm.patchValue({
      id: persona.id,
      codigo: persona.codigo,
      nombres: persona.nombres,
      apellidos: persona.apellidos,
      fechaNacimiento: persona.fechaNacimiento,
      correoElectronico: persona.correoElectronico,
      telefono: persona.telefono,
      usuarioId: persona.usuarioId,
      paisId: persona.paisId,
      cuentaId: persona.cuentaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  private createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nombres: this.editForm.get(['nombres'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      correoElectronico: this.editForm.get(['correoElectronico'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      paisId: this.editForm.get(['paisId'])!.value,
      cuentaId: this.editForm.get(['cuentaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>): void {
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
