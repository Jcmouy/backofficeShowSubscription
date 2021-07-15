import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { TipoDeObraDetailComponent } from 'app/entities/tipo-de-obra/tipo-de-obra-detail.component';
import { TipoDeObra } from 'app/shared/model/tipo-de-obra.model';

describe('Component Tests', () => {
  describe('TipoDeObra Management Detail Component', () => {
    let comp: TipoDeObraDetailComponent;
    let fixture: ComponentFixture<TipoDeObraDetailComponent>;
    const route = ({ data: of({ tipoDeObra: new TipoDeObra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [TipoDeObraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoDeObraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoDeObraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoDeObra on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoDeObra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
