import {NgModule} from '@angular/core';

import {
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatGridListModule,
    MatInputModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule
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
        MatButtonModule,
        MatTableModule,
        MatPaginatorModule,

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
        MatButtonModule,
        MatTableModule,
        MatPaginatorModule
    ]
})
export class MaterialModule {
}
