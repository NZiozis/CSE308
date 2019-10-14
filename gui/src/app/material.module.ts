import {NgModule} from '@angular/core';

import {
  MatToolbarModule,
  MatCardModule,
  MatSidenavModule,
  MatTabsModule,
  MatSliderModule,
  MatSelectModule,
  MatFormFieldModule,
  MatInputModule,

} from '@angular/material';

@NgModule({
  imports: [
    MatToolbarModule,
    MatCardModule,
    MatSidenavModule,
    MatTabsModule,
    MatSliderModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    MatToolbarModule,
    MatCardModule,
    MatSidenavModule,
    MatTabsModule,
    MatSliderModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
  ]
})
export class MaterialModule {}