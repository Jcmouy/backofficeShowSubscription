import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from './cuenta.service';

@Component({
  templateUrl: './cuenta-delete-dialog.component.html',
})
export class CuentaDeleteDialogComponent {
  cuenta?: ICuenta;

  constructor(protected cuentaService: CuentaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cuentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cuentaListModification');
      this.activeModal.close();
    });
  }
}
