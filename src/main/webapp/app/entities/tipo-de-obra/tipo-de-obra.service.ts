import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoDeObra } from 'app/shared/model/tipo-de-obra.model';

type EntityResponseType = HttpResponse<ITipoDeObra>;
type EntityArrayResponseType = HttpResponse<ITipoDeObra[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeObraService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-de-obras';

  constructor(protected http: HttpClient) {}

  create(tipoDeObra: ITipoDeObra): Observable<EntityResponseType> {
    return this.http.post<ITipoDeObra>(this.resourceUrl, tipoDeObra, { observe: 'response' });
  }

  update(tipoDeObra: ITipoDeObra): Observable<EntityResponseType> {
    return this.http.put<ITipoDeObra>(this.resourceUrl, tipoDeObra, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoDeObra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoDeObra[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
