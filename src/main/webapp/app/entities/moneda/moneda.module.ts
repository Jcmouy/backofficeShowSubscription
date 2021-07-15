import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { MonedaComponent } from './moneda.component';
import { MonedaDetailComponent } from './moneda-detail.component';
import { MonedaUpdateComponent } from './moneda-update.component';
import { MonedaDeleteDialogComponent } from './moneda-delete-dialog.component';
import { monedaRoute } from './moneda.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(monedaRoute)],
  declarations: [MonedaComponent, MonedaDetailComponent, MonedaUpdateComponent, MonedaDeleteDialogComponent],
  entryComponents: [MonedaDeleteDialogComponent],
})
export class PlataformaBackofficeMonedaModule {}
