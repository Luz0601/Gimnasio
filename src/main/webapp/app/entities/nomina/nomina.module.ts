import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  NominaComponent,
  NominaDetailComponent,
  NominaUpdateComponent,
  NominaDeletePopupComponent,
  NominaDeleteDialogComponent,
  nominaRoute,
  nominaPopupRoute
} from './';

const ENTITY_STATES = [...nominaRoute, ...nominaPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NominaComponent, NominaUpdateComponent, NominaDeleteDialogComponent, NominaDeletePopupComponent],
  entryComponents: [NominaComponent, NominaUpdateComponent, NominaDeleteDialogComponent, NominaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioNominaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
