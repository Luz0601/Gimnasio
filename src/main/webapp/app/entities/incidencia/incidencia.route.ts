import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Incidencia } from 'app/shared/model/incidencia.model';
import { IncidenciaService } from './incidencia.service';
import { IncidenciaComponent } from './incidencia.component';
import { IncidenciaDetailComponent } from './incidencia-detail.component';
import { IncidenciaUpdateComponent } from './incidencia-update.component';
import { IncidenciaDeletePopupComponent } from './incidencia-delete-dialog.component';
import { IIncidencia } from 'app/shared/model/incidencia.model';

@Injectable({ providedIn: 'root' })
export class IncidenciaResolve implements Resolve<IIncidencia> {
  constructor(private service: IncidenciaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncidencia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Incidencia>) => response.ok),
        map((incidencia: HttpResponse<Incidencia>) => incidencia.body)
      );
    }
    return of(new Incidencia());
  }
}

export const incidenciaRoute: Routes = [
  {
    path: '',
    component: IncidenciaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.incidencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidenciaDetailComponent,
    resolve: {
      incidencia: IncidenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.incidencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncidenciaUpdateComponent,
    resolve: {
      incidencia: IncidenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.incidencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncidenciaUpdateComponent,
    resolve: {
      incidencia: IncidenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.incidencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const incidenciaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IncidenciaDeletePopupComponent,
    resolve: {
      incidencia: IncidenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.incidencia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
