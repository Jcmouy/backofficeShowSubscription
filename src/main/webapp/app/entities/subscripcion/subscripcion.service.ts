import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubscripcion } from 'app/shared/model/subscripcion.model';

type EntityResponseType = HttpResponse<ISubscripcion>;
type EntityArrayResponseType = HttpResponse<ISubscripcion[]>;

@Injectable({ providedIn: 'root' })
export class SubscripcionService {
  public resourceUrl = SERVER_API_URL + 'api/subscripciones';

  constructor(protected http: HttpClient) {}

  create(subscripcion: ISubscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscripcion);
    return this.http
      .post<ISubscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscripcion: ISubscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscripcion);
    return this.http
      .put<ISubscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubscripcion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscripcion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subscripcion: ISubscripcion): ISubscripcion {
    const copy: ISubscripcion = Object.assign({}, subscripcion, {
      fecha: subscripcion.fecha && subscripcion.fecha.isValid() ? subscripcion.fecha.toJSON() : undefined,
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
      res.body.forEach((subscripcion: ISubscripcion) => {
        subscripcion.fecha = subscripcion.fecha ? moment(subscripcion.fecha) : undefined;
      });
    }
    return res;
  }
}
