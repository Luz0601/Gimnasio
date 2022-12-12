import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Clase } from 'app/shared/model/clase.model';
import { ClaseService } from './clase.service';
import { ClaseComponent } from './clase.component';
import { ClaseDetailComponent } from './clase-detail.component';
import { ClaseUpdateComponent } from './clase-update.component';
import { ClaseDeletePopupComponent } from './clase-delete-dialog.component';
import { IClase } from 'app/shared/model/clase.model';

@Injectable({ providedIn: 'root' })
export class ClaseResolve implements Resolve<IClase> {
  constructor(private service: ClaseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClase> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Clase>) => response.ok),
        map((clase: HttpResponse<Clase>) => clase.body)
      );
    }
    return of(new Clase());
  }
}

export const claseRoute: Routes = [
  {
    path: '',
    component: ClaseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER']['ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.clase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClaseDetailComponent,
    resolve: {
      clase: ClaseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.clase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClaseUpdateComponent,
    resolve: {
      clase: ClaseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.clase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClaseUpdateComponent,
    resolve: {
      clase: ClaseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.clase.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const clasePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClaseDeletePopupComponent,
    resolve: {
      clase: ClaseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.clase.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
