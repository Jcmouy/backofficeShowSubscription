import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPago } from 'app/shared/model/pago.model';
import { PagoService } from './pago.service';

@Component({
  templateUrl: './pago-delete-dialog.component.html',
})
export class PagoDeleteDialogComponent {
  pago?: IPago;

  constructor(protected pagoService: PagoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pagoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pagoListModification');
      this.activeModal.close();
    });
  }
}
