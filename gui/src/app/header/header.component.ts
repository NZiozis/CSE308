import { Component, OnInit } from '@angular/core';
import { HomeMapComponent } from './../home-map/home-map.component';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }

    zoomState(button) {
        const selectedState = button.explicitOriginalTarget.textContent
    }

}
