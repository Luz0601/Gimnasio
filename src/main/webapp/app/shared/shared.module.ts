import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GimnasioSharedLibsModule, GimnasioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GimnasioSharedLibsModule, GimnasioSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [GimnasioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioSharedModule {
  static forRoot() {
    return {
      ngModule: GimnasioSharedModule
    };
  }
}
