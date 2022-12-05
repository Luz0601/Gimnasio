import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  SuscripcionComponent,
  SuscripcionDetailComponent,
  SuscripcionUpdateComponent,
  SuscripcionDeletePopupComponent,
  SuscripcionDeleteDialogComponent,
  suscripcionRoute,
  suscripcionPopupRoute
} from './';

const ENTITY_STATES = [...suscripcionRoute, ...suscripcionPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SuscripcionComponent,
    SuscripcionDetailComponent,
    SuscripcionUpdateComponent,
    SuscripcionDeleteDialogComponent,
    SuscripcionDeletePopupComponent
  ],
  entryComponents: [SuscripcionComponent, SuscripcionUpdateComponent, SuscripcionDeleteDialogComponent, SuscripcionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioSuscripcionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
