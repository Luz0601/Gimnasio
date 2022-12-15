import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Nomina } from 'app/shared/model/nomina.model';
import { NominaService } from './nomina.service';
import { NominaComponent } from './nomina.component';
import { NominaDetailComponent } from './nomina-detail.component';
import { NominaUpdateComponent } from './nomina-update.component';
import { NominaDeletePopupComponent } from './nomina-delete-dialog.component';
import { INomina } from 'app/shared/model/nomina.model';

@Injectable({ providedIn: 'root' })
export class NominaResolve implements Resolve<INomina> {
  constructor(private service: NominaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INomina> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Nomina>) => response.ok),
        map((nomina: HttpResponse<Nomina>) => nomina.body)
      );
    }
    return of(new Nomina());
  }
}

export const nominaRoute: Routes = [
  {
    path: '',
    component: NominaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.nomina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NominaDetailComponent,
    resolve: {
      nomina: NominaResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.nomina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NominaUpdateComponent,
    resolve: {
      nomina: NominaResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.nomina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NominaUpdateComponent,
    resolve: {
      nomina: NominaResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.nomina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nominaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NominaDeletePopupComponent,
    resolve: {
      nomina: NominaResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.nomina.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
