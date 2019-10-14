import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slider-data',
  templateUrl: './slider-data.component.html',
  styleUrls: ['./slider-data.component.css']
})
export class SliderDataComponent implements OnInit {
    autoTicks = false;
    disabled = false;
    invert = false;
    max = 100;
    min = 0;
    showTicks = false;
    step = 1;
    thumbLabel = false;
    value = 0;
    vertical = false;

    constructor() { }

    ngOnInit() {
    }

}
