import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFuncion } from 'app/shared/model/funcion.model';

type EntityResponseType = HttpResponse<IFuncion>;
type EntityArrayResponseType = HttpResponse<IFuncion[]>;

@Injectable({ providedIn: 'root' })
export class FuncionService {
  public resourceUrl = SERVER_API_URL + 'api/funciones';

  constructor(protected http: HttpClient) {}

  create(funcion: IFuncion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcion);
    return this.http
      .post<IFuncion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(funcion: IFuncion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcion);
    return this.http
      .put<IFuncion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFuncion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFuncion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(funcion: IFuncion): IFuncion {
    const copy: IFuncion = Object.assign({}, funcion, {
      fecha: funcion.fecha && funcion.fecha.isValid() ? funcion.fecha.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((funcion: IFuncion) => {
        funcion.fecha = funcion.fecha ? moment(funcion.fecha) : undefined;
      });
    }
    return res;
  }
}
