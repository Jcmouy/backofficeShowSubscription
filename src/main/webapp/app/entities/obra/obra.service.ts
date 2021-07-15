import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObra } from 'app/shared/model/obra.model';

type EntityResponseType = HttpResponse<IObra>;
type EntityArrayResponseType = HttpResponse<IObra[]>;

@Injectable({ providedIn: 'root' })
export class ObraService {
  public resourceUrl = SERVER_API_URL + 'api/obras';

  constructor(protected http: HttpClient) {}

  create(obra: IObra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obra);
    return this.http
      .post<IObra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(obra: IObra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obra);
    return this.http
      .put<IObra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(obra: IObra): IObra {
    const copy: IObra = Object.assign({}, obra, {
      fecha: obra.fecha && obra.fecha.isValid() ? obra.fecha.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((obra: IObra) => {
        obra.fecha = obra.fecha ? moment(obra.fecha) : undefined;
      });
    }
    return res;
  }
}
