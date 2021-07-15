import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { MonedaDetailComponent } from 'app/entities/moneda/moneda-detail.component';
import { Moneda } from 'app/shared/model/moneda.model';

describe('Component Tests', () => {
  describe('Moneda Management Detail Component', () => {
    let comp: MonedaDetailComponent;
    let fixture: ComponentFixture<MonedaDetailComponent>;
    const route = ({ data: of({ moneda: new Moneda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [MonedaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MonedaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MonedaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load moneda on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.moneda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
