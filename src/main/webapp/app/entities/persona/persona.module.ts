import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaBackofficeSharedModule } from 'app/shared/shared.module';
import { PersonaComponent } from './persona.component';
import { PersonaDetailComponent } from './persona-detail.component';
import { PersonaUpdateComponent } from './persona-update.component';
import { PersonaDeleteDialogComponent } from './persona-delete-dialog.component';
import { personaRoute } from './persona.route';

@NgModule({
  imports: [PlataformaBackofficeSharedModule, RouterModule.forChild(personaRoute)],
  declarations: [PersonaComponent, PersonaDetailComponent, PersonaUpdateComponent, PersonaDeleteDialogComponent],
  entryComponents: [PersonaDeleteDialogComponent],
})
export class PlataformaBackofficePersonaModule {}
