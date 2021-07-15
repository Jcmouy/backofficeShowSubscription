import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProceso } from 'app/shared/model/proceso.model';

type EntityResponseType = HttpResponse<IProceso>;
type EntityArrayResponseType = HttpResponse<IProceso[]>;

@Injectable({ providedIn: 'root' })
export class ProcesoService {
  public resourceUrl = SERVER_API_URL + 'api/procesos';

  constructor(protected http: HttpClient) {}

  create(proceso: IProceso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proceso);
    return this.http
      .post<IProceso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proceso: IProceso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proceso);
    return this.http
      .put<IProceso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProceso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProceso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proceso: IProceso): IProceso {
    const copy: IProceso = Object.assign({}, proceso, {
      fecha: proceso.fecha && proceso.fecha.isValid() ? proceso.fecha.toJSON() : undefined,
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
      res.body.forEach((proceso: IProceso) => {
        proceso.fecha = proceso.fecha ? moment(proceso.fecha) : undefined;
      });
    }
    return res;
  }
}
