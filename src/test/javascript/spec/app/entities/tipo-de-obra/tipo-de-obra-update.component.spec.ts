import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { TipoDeObraUpdateComponent } from 'app/entities/tipo-de-obra/tipo-de-obra-update.component';
import { TipoDeObraService } from 'app/entities/tipo-de-obra/tipo-de-obra.service';
import { TipoDeObra } from 'app/shared/model/tipo-de-obra.model';

describe('Component Tests', () => {
  describe('TipoDeObra Management Update Component', () => {
    let comp: TipoDeObraUpdateComponent;
    let fixture: ComponentFixture<TipoDeObraUpdateComponent>;
    let service: TipoDeObraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [TipoDeObraUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoDeObraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoDeObraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoDeObraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoDeObra(123);
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
        const entity = new TipoDeObra();
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
