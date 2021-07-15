import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMoneda } from 'app/shared/model/moneda.model';

type EntityResponseType = HttpResponse<IMoneda>;
type EntityArrayResponseType = HttpResponse<IMoneda[]>;

@Injectable({ providedIn: 'root' })
export class MonedaService {
  public resourceUrl = SERVER_API_URL + 'api/monedas';

  constructor(protected http: HttpClient) {}

  create(moneda: IMoneda): Observable<EntityResponseType> {
    return this.http.post<IMoneda>(this.resourceUrl, moneda, { observe: 'response' });
  }

  update(moneda: IMoneda): Observable<EntityResponseType> {
    return this.http.put<IMoneda>(this.resourceUrl, moneda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoneda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoneda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
