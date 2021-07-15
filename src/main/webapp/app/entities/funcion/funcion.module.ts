import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { FuncionComponent } from './funcion.component';
import { FuncionDetailComponent } from './funcion-detail.component';
import { FuncionUpdateComponent } from './funcion-update.component';
import { FuncionDeleteDialogComponent } from './funcion-delete-dialog.component';
import { funcionRoute } from './funcion.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(funcionRoute)],
  declarations: [FuncionComponent, FuncionDetailComponent, FuncionUpdateComponent, FuncionDeleteDialogComponent],
  entryComponents: [FuncionDeleteDialogComponent],
})
export class PlataformaBackofficeFuncionModule {}
