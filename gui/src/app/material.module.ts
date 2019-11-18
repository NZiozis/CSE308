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
  MatGridListModule,
  MatButtonModule
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
    MatGridListModule,
    MatButtonModule

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
    MatGridListModule,
    MatButtonModule
  ]
})
export class MaterialModule {}