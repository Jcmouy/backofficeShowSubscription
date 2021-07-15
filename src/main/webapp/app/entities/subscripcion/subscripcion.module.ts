import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { SubscripcionComponent } from './subscripcion.component';
import { SubscripcionDetailComponent } from './subscripcion-detail.component';
import { SubscripcionUpdateComponent } from './subscripcion-update.component';
import { SubscripcionDeleteDialogComponent } from './subscripcion-delete-dialog.component';
import { subscripcionRoute } from './subscripcion.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(subscripcionRoute)],
  declarations: [SubscripcionComponent, SubscripcionDetailComponent, SubscripcionUpdateComponent, SubscripcionDeleteDialogComponent],
  entryComponents: [SubscripcionDeleteDialogComponent],
})
export class PlataformaBackofficeSubscripcionModule {}
