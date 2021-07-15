import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { EtiquetaComponent } from './etiqueta.component';
import { EtiquetaDetailComponent } from './etiqueta-detail.component';
import { EtiquetaUpdateComponent } from './etiqueta-update.component';
import { EtiquetaDeleteDialogComponent } from './etiqueta-delete-dialog.component';
import { etiquetaRoute } from './etiqueta.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(etiquetaRoute)],
  declarations: [EtiquetaComponent, EtiquetaDetailComponent, EtiquetaUpdateComponent, EtiquetaDeleteDialogComponent],
  entryComponents: [EtiquetaDeleteDialogComponent],
})
export class PlataformaBackofficeEtiquetaModule {}
