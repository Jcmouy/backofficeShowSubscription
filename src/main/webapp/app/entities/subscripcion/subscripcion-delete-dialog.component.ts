import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubscripcion } from 'app/shared/model/subscripcion.model';
import { SubscripcionService } from './subscripcion.service';

@Component({
  templateUrl: './subscripcion-delete-dialog.component.html',
})
export class SubscripcionDeleteDialogComponent {
  subscripcion?: ISubscripcion;

  constructor(
    protected subscripcionService: SubscripcionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subscripcionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subscripcionListModification');
      this.activeModal.close();
    });
  }
}
