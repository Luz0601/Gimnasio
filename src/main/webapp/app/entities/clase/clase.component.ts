import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { IClase } from 'app/shared/model/clase.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ClaseService } from './clase.service';
import { ClaseDetailComponent } from './clase-detail.component';
import { ClaseUpdateComponent } from './clase-update.component';
import { IncidenciaDetailComponent } from '../incidencia/incidencia-detail.component';
import { EmpleadoDetailComponent } from '../empleado/empleado-detail.component';

@Component({
  selector: 'jhi-clase',
  templateUrl: './clase.component.html'
})
export class ClaseComponent implements OnInit, OnDestroy {
  currentAccount: any;
  clases: IClase[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected claseService: ClaseService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.claseService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IClase[]>) => this.paginateClases(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  open(content) {
    const modalRef = this.modalService.open(ClaseDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.clase = content;
  }

  editar(content) {
    const modalRef = this.modalService.open(ClaseUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.clase = content;
  }
  incidencia(content) {
    const modalRef = this.modalService.open(IncidenciaDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.incidencia = content;
  }
  monitor(content) {
    const modalRef = this.modalService.open(EmpleadoDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.empleado = content;
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/clase'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/clase',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInClases();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClase) {
    return item.id;
  }

  registerChangeInClases() {
    this.eventSubscriber = this.eventManager.subscribe('claseListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateClases(data: IClase[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.clases = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
