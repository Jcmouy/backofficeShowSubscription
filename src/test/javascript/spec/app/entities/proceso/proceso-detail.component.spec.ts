import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { ProcesoDetailComponent } from 'app/entities/proceso/proceso-detail.component';
import { Proceso } from 'app/shared/model/proceso.model';

describe('Component Tests', () => {
  describe('Proceso Management Detail Component', () => {
    let comp: ProcesoDetailComponent;
    let fixture: ComponentFixture<ProcesoDetailComponent>;
    const route = ({ data: of({ proceso: new Proceso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [ProcesoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProcesoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcesoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proceso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proceso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
