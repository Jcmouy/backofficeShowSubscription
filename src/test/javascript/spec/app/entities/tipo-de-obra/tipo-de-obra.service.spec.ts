import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TipoDeObraService } from 'app/entities/tipo-de-obra/tipo-de-obra.service';
import { ITipoDeObra, TipoDeObra } from 'app/shared/model/tipo-de-obra.model';
import { TiposDeObra } from 'app/shared/model/enumerations/tipos-de-obra.model';

describe('Service Tests', () => {
  describe('TipoDeObra Service', () => {
    let injector: TestBed;
    let service: TipoDeObraService;
    let httpMock: HttpTestingController;
    let elemDefault: ITipoDeObra;
    let expectedResult: ITipoDeObra | ITipoDeObra[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TipoDeObraService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TipoDeObra(0, TiposDeObra.CORTO, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TipoDeObra', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TipoDeObra()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TipoDeObra', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descripcion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TipoDeObra', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descripcion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TipoDeObra', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
