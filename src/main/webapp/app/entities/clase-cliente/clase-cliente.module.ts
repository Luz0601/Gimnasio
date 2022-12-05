import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  ClaseClienteComponent,
  ClaseClienteDetailComponent,
  ClaseClienteUpdateComponent,
  ClaseClienteDeletePopupComponent,
  ClaseClienteDeleteDialogComponent,
  claseClienteRoute,
  claseClientePopupRoute
} from './';

const ENTITY_STATES = [...claseClienteRoute, ...claseClientePopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClaseClienteComponent,
    ClaseClienteDetailComponent,
    ClaseClienteUpdateComponent,
    ClaseClienteDeleteDialogComponent,
    ClaseClienteDeletePopupComponent
  ],
  entryComponents: [
    ClaseClienteComponent,
    ClaseClienteUpdateComponent,
    ClaseClienteDeleteDialogComponent,
    ClaseClienteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioClaseClienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
