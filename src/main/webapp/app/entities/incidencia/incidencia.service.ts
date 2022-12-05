import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncidencia } from 'app/shared/model/incidencia.model';

type EntityResponseType = HttpResponse<IIncidencia>;
type EntityArrayResponseType = HttpResponse<IIncidencia[]>;

@Injectable({ providedIn: 'root' })
export class IncidenciaService {
  public resourceUrl = SERVER_API_URL + 'api/incidencias';

  constructor(protected http: HttpClient) {}

  create(incidencia: IIncidencia): Observable<EntityResponseType> {
    return this.http.post<IIncidencia>(this.resourceUrl, incidencia, { observe: 'response' });
  }

  update(incidencia: IIncidencia): Observable<EntityResponseType> {
    return this.http.put<IIncidencia>(this.resourceUrl, incidencia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIncidencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIncidencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
