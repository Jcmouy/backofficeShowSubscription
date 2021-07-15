import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoneda } from 'app/shared/model/moneda.model';
import { MonedaService } from './moneda.service';

@Component({
  templateUrl: './moneda-delete-dialog.component.html',
})
export class MonedaDeleteDialogComponent {
  moneda?: IMoneda;

  constructor(protected monedaService: MonedaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monedaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('monedaListModification');
      this.activeModal.close();
    });
  }
}
