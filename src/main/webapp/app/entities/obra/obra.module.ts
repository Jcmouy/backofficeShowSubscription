import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { ObraComponent } from './obra.component';
import { ObraDetailComponent } from './obra-detail.component';
import { ObraUpdateComponent } from './obra-update.component';
import { ObraDeleteDialogComponent } from './obra-delete-dialog.component';
import { obraRoute } from './obra.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(obraRoute)],
  declarations: [ObraComponent, ObraDetailComponent, ObraUpdateComponent, ObraDeleteDialogComponent],
  entryComponents: [ObraDeleteDialogComponent],
})
export class PlataformaBackofficeObraModule {}
