import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { TipoDeObraComponent } from './tipo-de-obra.component';
import { TipoDeObraDetailComponent } from './tipo-de-obra-detail.component';
import { TipoDeObraUpdateComponent } from './tipo-de-obra-update.component';
import { TipoDeObraDeleteDialogComponent } from './tipo-de-obra-delete-dialog.component';
import { tipoDeObraRoute } from './tipo-de-obra.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(tipoDeObraRoute)],
  declarations: [TipoDeObraComponent, TipoDeObraDetailComponent, TipoDeObraUpdateComponent, TipoDeObraDeleteDialogComponent],
  entryComponents: [TipoDeObraDeleteDialogComponent],
})
export class PlataformaBackofficeTipoDeObraModule {}
