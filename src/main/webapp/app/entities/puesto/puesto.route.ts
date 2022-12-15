import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Puesto } from 'app/shared/model/puesto.model';
import { PuestoService } from './puesto.service';
import { PuestoComponent } from './puesto.component';
import { PuestoDetailComponent } from './puesto-detail.component';
import { PuestoUpdateComponent } from './puesto-update.component';
import { PuestoDeletePopupComponent } from './puesto-delete-dialog.component';
import { IPuesto } from 'app/shared/model/puesto.model';

@Injectable({ providedIn: 'root' })
export class PuestoResolve implements Resolve<IPuesto> {
  constructor(private service: PuestoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPuesto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Puesto>) => response.ok),
        map((puesto: HttpResponse<Puesto>) => puesto.body)
      );
    }
    return of(new Puesto());
  }
}

export const puestoRoute: Routes = [
  {
    path: '',
    component: PuestoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.puesto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PuestoDetailComponent,
    resolve: {
      puesto: PuestoResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.puesto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PuestoUpdateComponent,
    resolve: {
      puesto: PuestoResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.puesto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PuestoUpdateComponent,
    resolve: {
      puesto: PuestoResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.puesto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const puestoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PuestoDeletePopupComponent,
    resolve: {
      puesto: PuestoResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.puesto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
