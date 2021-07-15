import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaBackofficeTestModule } from '../../../test.module';
import { CuentaDetailComponent } from 'app/entities/cuenta/cuenta-detail.component';
import { Cuenta } from 'app/shared/model/cuenta.model';

describe('Component Tests', () => {
  describe('Cuenta Management Detail Component', () => {
    let comp: CuentaDetailComponent;
    let fixture: ComponentFixture<CuentaDetailComponent>;
    const route = ({ data: of({ cuenta: new Cuenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PlataformaBackofficeTestModule],
        declarations: [CuentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CuentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CuentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cuenta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cuenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
