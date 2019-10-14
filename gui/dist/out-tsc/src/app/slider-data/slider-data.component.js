import { __decorate } from "tslib";
import { Component } from '@angular/core';
let SliderDataComponent = class SliderDataComponent {
    constructor() {
        this.autoTicks = false;
        this.disabled = false;
        this.invert = false;
        this.max = 100;
        this.min = 0;
        this.showTicks = false;
        this.step = 1;
        this.thumbLabel = false;
        this.value = 0;
        this.vertical = false;
    }
    ngOnInit() {
    }
};
SliderDataComponent = __decorate([
    Component({
        selector: 'app-slider-data',
        templateUrl: './slider-data.component.html',
        styleUrls: ['./slider-data.component.css']
    })
], SliderDataComponent);
export { SliderDataComponent };
//# sourceMappingURL=slider-data.component.js.map