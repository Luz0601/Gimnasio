import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  InventarioComponent,
  InventarioDetailComponent,
  InventarioUpdateComponent,
  InventarioDeletePopupComponent,
  InventarioDeleteDialogComponent,
  inventarioRoute,
  inventarioPopupRoute
} from './';

const ENTITY_STATES = [...inventarioRoute, ...inventarioPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventarioComponent,
    InventarioDetailComponent,
    InventarioUpdateComponent,
    InventarioDeleteDialogComponent,
    InventarioDeletePopupComponent
  ],
  entryComponents: [InventarioComponent, InventarioUpdateComponent, InventarioDeleteDialogComponent, InventarioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioInventarioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
