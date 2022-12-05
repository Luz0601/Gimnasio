import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  ClienteSuscripcionComponent,
  ClienteSuscripcionDetailComponent,
  ClienteSuscripcionUpdateComponent,
  ClienteSuscripcionDeletePopupComponent,
  ClienteSuscripcionDeleteDialogComponent,
  clienteSuscripcionRoute,
  clienteSuscripcionPopupRoute
} from './';

const ENTITY_STATES = [...clienteSuscripcionRoute, ...clienteSuscripcionPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteSuscripcionComponent,
    ClienteSuscripcionDetailComponent,
    ClienteSuscripcionUpdateComponent,
    ClienteSuscripcionDeleteDialogComponent,
    ClienteSuscripcionDeletePopupComponent
  ],
  entryComponents: [
    ClienteSuscripcionComponent,
    ClienteSuscripcionUpdateComponent,
    ClienteSuscripcionDeleteDialogComponent,
    ClienteSuscripcionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioClienteSuscripcionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
