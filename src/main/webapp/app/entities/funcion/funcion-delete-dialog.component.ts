import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuncion } from 'app/shared/model/funcion.model';
import { FuncionService } from './funcion.service';

@Component({
  templateUrl: './funcion-delete-dialog.component.html',
})
export class FuncionDeleteDialogComponent {
  funcion?: IFuncion;

  constructor(protected funcionService: FuncionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('funcionListModification');
      this.activeModal.close();
    });
  }
}
