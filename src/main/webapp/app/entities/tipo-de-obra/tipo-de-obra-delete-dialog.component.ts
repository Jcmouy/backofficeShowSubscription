import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeObra } from 'app/shared/model/tipo-de-obra.model';
import { TipoDeObraService } from './tipo-de-obra.service';

@Component({
  templateUrl: './tipo-de-obra-delete-dialog.component.html',
})
export class TipoDeObraDeleteDialogComponent {
  tipoDeObra?: ITipoDeObra;

  constructor(
    protected tipoDeObraService: TipoDeObraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoDeObraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoDeObraListModification');
      this.activeModal.close();
    });
  }
}
