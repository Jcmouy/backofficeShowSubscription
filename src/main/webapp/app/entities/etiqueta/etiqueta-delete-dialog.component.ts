import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtiqueta } from 'app/shared/model/etiqueta.model';
import { EtiquetaService } from './etiqueta.service';

@Component({
  templateUrl: './etiqueta-delete-dialog.component.html',
})
export class EtiquetaDeleteDialogComponent {
  etiqueta?: IEtiqueta;

  constructor(protected etiquetaService: EtiquetaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etiquetaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etiquetaListModification');
      this.activeModal.close();
    });
  }
}
