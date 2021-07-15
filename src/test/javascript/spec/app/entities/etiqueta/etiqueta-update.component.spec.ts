import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { EtiquetaUpdateComponent } from 'app/entities/etiqueta/etiqueta-update.component';
import { EtiquetaService } from 'app/entities/etiqueta/etiqueta.service';
import { Etiqueta } from 'app/shared/model/etiqueta.model';

describe('Component Tests', () => {
  describe('Etiqueta Management Update Component', () => {
    let comp: EtiquetaUpdateComponent;
    let fixture: ComponentFixture<EtiquetaUpdateComponent>;
    let service: EtiquetaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [EtiquetaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EtiquetaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtiquetaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtiquetaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etiqueta(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etiqueta();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
