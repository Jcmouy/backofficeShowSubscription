import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtiqueta } from 'app/shared/model/etiqueta.model';

type EntityResponseType = HttpResponse<IEtiqueta>;
type EntityArrayResponseType = HttpResponse<IEtiqueta[]>;

@Injectable({ providedIn: 'root' })
export class EtiquetaService {
  public resourceUrl = SERVER_API_URL + 'api/etiquetas';

  constructor(protected http: HttpClient) {}

  create(etiqueta: IEtiqueta): Observable<EntityResponseType> {
    return this.http.post<IEtiqueta>(this.resourceUrl, etiqueta, { observe: 'response' });
  }

  update(etiqueta: IEtiqueta): Observable<EntityResponseType> {
    return this.http.put<IEtiqueta>(this.resourceUrl, etiqueta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtiqueta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtiqueta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
