import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProceso } from 'app/shared/model/proceso.model';
import { ProcesoService } from './proceso.service';

@Component({
  templateUrl: './proceso-delete-dialog.component.html',
})
export class ProcesoDeleteDialogComponent {
  proceso?: IProceso;

  constructor(protected procesoService: ProcesoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.procesoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('procesoListModification');
      this.activeModal.close();
    });
  }
}
