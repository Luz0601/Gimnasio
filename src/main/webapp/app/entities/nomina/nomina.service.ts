import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INomina } from 'app/shared/model/nomina.model';

type EntityResponseType = HttpResponse<INomina>;
type EntityArrayResponseType = HttpResponse<INomina[]>;

@Injectable({ providedIn: 'root' })
export class NominaService {
  public resourceUrl = SERVER_API_URL + 'api/nominas';

  constructor(protected http: HttpClient) {}

  create(nomina: INomina): Observable<EntityResponseType> {
    return this.http.post<INomina>(this.resourceUrl, nomina, { observe: 'response' });
  }

  update(nomina: INomina): Observable<EntityResponseType> {
    return this.http.put<INomina>(this.resourceUrl, nomina, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INomina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INomina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
