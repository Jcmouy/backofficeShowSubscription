import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { ContenidoDetailComponent } from 'app/entities/contenido/contenido-detail.component';
import { Contenido } from 'app/shared/model/contenido.model';

describe('Component Tests', () => {
  describe('Contenido Management Detail Component', () => {
    let comp: ContenidoDetailComponent;
    let fixture: ComponentFixture<ContenidoDetailComponent>;
    const route = ({ data: of({ contenido: new Contenido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [ContenidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContenidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContenidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contenido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contenido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
