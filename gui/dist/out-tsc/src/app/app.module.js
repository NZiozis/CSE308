import { __decorate } from "tslib";
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { HomeMapComponent } from './home-map/home-map.component';
import { MaterialModule } from './material.module';
import { SliderDataComponent } from './slider-data/slider-data.component';
import { LeftPanelComponent } from './left-panel/left-panel.component';
import { MiddlePanelComponent } from './middle-panel/middle-panel.component';
import { RightPanelComponent } from './right-panel/right-panel.component';
let AppModule = class AppModule {
};
AppModule = __decorate([
    NgModule({
        declarations: [
            AppComponent,
            HomeMapComponent,
            SliderDataComponent,
            LeftPanelComponent,
            MiddlePanelComponent,
            RightPanelComponent
        ],
        imports: [
            BrowserModule,
            AppRoutingModule,
            MaterialModule,
            BrowserAnimationsModule,
            LeafletModule.forRoot(),
            FormsModule
        ],
        providers: [],
        bootstrap: [AppComponent]
    })
], AppModule);
export { AppModule };
//# sourceMappingURL=app.module.js.map