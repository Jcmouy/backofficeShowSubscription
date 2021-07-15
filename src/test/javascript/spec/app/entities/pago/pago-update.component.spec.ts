import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { PagoUpdateComponent } from 'app/entities/pago/pago-update.component';
import { PagoService } from 'app/entities/pago/pago.service';
import { Pago } from 'app/shared/model/pago.model';

describe('Component Tests', () => {
  describe('Pago Management Update Component', () => {
    let comp: PagoUpdateComponent;
    let fixture: ComponentFixture<PagoUpdateComponent>;
    let service: PagoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [PagoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PagoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PagoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PagoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pago(123);
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
        const entity = new Pago();
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
