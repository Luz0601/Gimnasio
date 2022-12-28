import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core';
import { IHorarioClase } from 'app/shared/model/horario-clase.model';
import * as moment from 'moment';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { HorarioClaseService } from './horario-clase.service';

@Component({
  selector: 'jhi-horario-clase',
  templateUrl: './horario-clase.component.html'
})
export class HorarioClaseComponent implements OnInit, OnDestroy {
  horarioClases: IHorarioClase[];

  eventSubscriber: Subscription;
  currentAccount: any;
  links: any;
  totalItems: any;

  routeData: any;
  date: any;
  month: any;
  week: any;
  day: any;
  // reverse: any;

  constructor(
    protected horarioClaseService: HorarioClaseService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute
  ) {
    this.routeData = this.activatedRoute.data.subscribe(data => {
      // this.month = data.
    });
  }

  loadAll() {
    this.horarioClaseService
      .query({
        date: this.date.format('YYYY-MM-DD HH:mm:ss'),
        month: this.month,
        week: this.week,
        day: this.day
      })
      .subscribe(
        (res: HttpResponse<IHorarioClase[]>) => this.listaHorarioClase(res.body), //, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit(): void {
    this.date = moment();
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHorarioClases();
  }

  ngOnDestroy(): void {
    this.eventManager.destroy(this.eventSubscriber);
  }

  registerChangeInHorarioClases() {
    this.eventSubscriber = this.eventManager.subscribe('claseClienteListModification', response => this.loadAll());
  }

  trackClaseId(index: number, item: IHorarioClase) {
    return item.claseId;
  }

  protected listaHorarioClase(data: IHorarioClase[]) {
    // , headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.horarioClases = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
