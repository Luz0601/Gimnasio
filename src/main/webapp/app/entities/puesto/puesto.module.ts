import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  PuestoComponent,
  PuestoDetailComponent,
  PuestoUpdateComponent,
  PuestoDeletePopupComponent,
  PuestoDeleteDialogComponent,
  puestoRoute,
  puestoPopupRoute
} from './';

const ENTITY_STATES = [...puestoRoute, ...puestoPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PuestoComponent, PuestoDetailComponent, PuestoUpdateComponent, PuestoDeleteDialogComponent, PuestoDeletePopupComponent],
  entryComponents: [PuestoComponent, PuestoUpdateComponent, PuestoDeleteDialogComponent, PuestoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioPuestoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
