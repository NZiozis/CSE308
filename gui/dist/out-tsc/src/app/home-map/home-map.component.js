import { __decorate } from "tslib";
import { Component } from '@angular/core';
import { tileLayer, latLng } from 'leaflet';
let HomeMapComponent = class HomeMapComponent {
    constructor() {
        this.options = {
            layers: [
                tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' }),
            ],
            zoom: 4,
            center: latLng(37.6, -95.665)
        };
    }
    ngOnInit() {
    }
};
HomeMapComponent = __decorate([
    Component({
        selector: 'app-home-map',
        templateUrl: './home-map.component.html',
        styleUrls: ['./home-map.component.css']
    })
], HomeMapComponent);
export { HomeMapComponent };
//# sourceMappingURL=home-map.component.js.map