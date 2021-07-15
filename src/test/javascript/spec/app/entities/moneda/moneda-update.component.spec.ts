import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { MonedaUpdateComponent } from 'app/entities/moneda/moneda-update.component';
import { MonedaService } from 'app/entities/moneda/moneda.service';
import { Moneda } from 'app/shared/model/moneda.model';

describe('Component Tests', () => {
  describe('Moneda Management Update Component', () => {
    let comp: MonedaUpdateComponent;
    let fixture: ComponentFixture<MonedaUpdateComponent>;
    let service: MonedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [MonedaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MonedaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonedaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MonedaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Moneda(123);
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
        const entity = new Moneda();
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
