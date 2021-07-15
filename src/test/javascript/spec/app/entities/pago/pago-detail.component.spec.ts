import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { PagoDetailComponent } from 'app/entities/pago/pago-detail.component';
import { Pago } from 'app/shared/model/pago.model';

describe('Component Tests', () => {
  describe('Pago Management Detail Component', () => {
    let comp: PagoDetailComponent;
    let fixture: ComponentFixture<PagoDetailComponent>;
    const route = ({ data: of({ pago: new Pago(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [PagoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PagoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PagoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pago on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pago).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
