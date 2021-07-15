import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TipoDeObraDeleteDialogComponent } from 'app/entities/tipo-de-obra/tipo-de-obra-delete-dialog.component';
import { TipoDeObraService } from 'app/entities/tipo-de-obra/tipo-de-obra.service';

describe('Component Tests', () => {
  describe('TipoDeObra Management Delete Component', () => {
    let comp: TipoDeObraDeleteDialogComponent;
    let fixture: ComponentFixture<TipoDeObraDeleteDialogComponent>;
    let service: TipoDeObraService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [TipoDeObraDeleteDialogComponent],
      })
        .overrideTemplate(TipoDeObraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoDeObraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoDeObraService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
