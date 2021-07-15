import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObra } from 'app/shared/model/obra.model';
import { ObraService } from './obra.service';

@Component({
  templateUrl: './obra-delete-dialog.component.html',
})
export class ObraDeleteDialogComponent {
  obra?: IObra;

  constructor(protected obraService: ObraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.obraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('obraListModification');
      this.activeModal.close();
    });
  }
}
