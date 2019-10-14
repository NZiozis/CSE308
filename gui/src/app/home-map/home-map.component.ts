import { Component, OnInit } from '@angular/core';
import { tileLayer, latLng } from 'leaflet';

@Component({
    selector: 'app-home-map',
    templateUrl: './home-map.component.html',
    styleUrls: ['./home-map.component.css']
})
export class HomeMapComponent implements OnInit {
    options = {
        layers: [
            tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })
        ],
        zoom: 5,
        center: latLng(37.6, -95.665)
    };

    constructor() { }

    ngOnInit() {
    }

}
