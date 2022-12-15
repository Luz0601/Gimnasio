import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';
import { ClienteSuscripcionService } from './cliente-suscripcion.service';
import { ClienteSuscripcionComponent } from './cliente-suscripcion.component';
import { ClienteSuscripcionDetailComponent } from './cliente-suscripcion-detail.component';
import { ClienteSuscripcionUpdateComponent } from './cliente-suscripcion-update.component';
import { ClienteSuscripcionDeletePopupComponent } from './cliente-suscripcion-delete-dialog.component';
import { IClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';

@Injectable({ providedIn: 'root' })
export class ClienteSuscripcionResolve implements Resolve<IClienteSuscripcion> {
  constructor(private service: ClienteSuscripcionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClienteSuscripcion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ClienteSuscripcion>) => response.ok),
        map((clienteSuscripcion: HttpResponse<ClienteSuscripcion>) => clienteSuscripcion.body)
      );
    }
    return of(new ClienteSuscripcion());
  }
}

export const clienteSuscripcionRoute: Routes = [
  {
    path: '',
    component: ClienteSuscripcionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.clienteSuscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClienteSuscripcionDetailComponent,
    resolve: {
      clienteSuscripcion: ClienteSuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.clienteSuscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClienteSuscripcionUpdateComponent,
    resolve: {
      clienteSuscripcion: ClienteSuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.clienteSuscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClienteSuscripcionUpdateComponent,
    resolve: {
      clienteSuscripcion: ClienteSuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.clienteSuscripcion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const clienteSuscripcionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClienteSuscripcionDeletePopupComponent,
    resolve: {
      clienteSuscripcion: ClienteSuscripcionResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.clienteSuscripcion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
