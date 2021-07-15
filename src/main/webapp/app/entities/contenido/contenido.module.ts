import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { ContenidoComponent } from './contenido.component';
import { ContenidoDetailComponent } from './contenido-detail.component';
import { ContenidoUpdateComponent } from './contenido-update.component';
import { ContenidoDeleteDialogComponent } from './contenido-delete-dialog.component';
import { contenidoRoute } from './contenido.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(contenidoRoute)],
  declarations: [ContenidoComponent, ContenidoDetailComponent, ContenidoUpdateComponent, ContenidoDeleteDialogComponent],
  entryComponents: [ContenidoDeleteDialogComponent],
})
export class PlataformaBackofficeContenidoModule {}
