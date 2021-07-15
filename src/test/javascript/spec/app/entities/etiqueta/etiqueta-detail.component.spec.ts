import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { EtiquetaDetailComponent } from 'app/entities/etiqueta/etiqueta-detail.component';
import { Etiqueta } from 'app/shared/model/etiqueta.model';

describe('Component Tests', () => {
  describe('Etiqueta Management Detail Component', () => {
    let comp: EtiquetaDetailComponent;
    let fixture: ComponentFixture<EtiquetaDetailComponent>;
    const route = ({ data: of({ etiqueta: new Etiqueta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [EtiquetaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EtiquetaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtiquetaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etiqueta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etiqueta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
