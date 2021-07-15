import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { PagoComponent } from './pago.component';
import { PagoDetailComponent } from './pago-detail.component';
import { PagoUpdateComponent } from './pago-update.component';
import { PagoDeleteDialogComponent } from './pago-delete-dialog.component';
import { pagoRoute } from './pago.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(pagoRoute)],
  declarations: [PagoComponent, PagoDetailComponent, PagoUpdateComponent, PagoDeleteDialogComponent],
  entryComponents: [PagoDeleteDialogComponent],
})
export class PlataformaBackofficePagoModule {}
