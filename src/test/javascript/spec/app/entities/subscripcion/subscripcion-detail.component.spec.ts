import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { SubscripcionDetailComponent } from 'app/entities/subscripcion/subscripcion-detail.component';
import { Subscripcion } from 'app/shared/model/subscripcion.model';

describe('Component Tests', () => {
  describe('Subscripcion Management Detail Component', () => {
    let comp: SubscripcionDetailComponent;
    let fixture: ComponentFixture<SubscripcionDetailComponent>;
    const route = ({ data: of({ subscripcion: new Subscripcion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [SubscripcionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubscripcionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubscripcionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subscripcion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subscripcion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
