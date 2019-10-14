import { Component, OnInit } from '@angular/core';
import { geoJSON, Map, tileLayer, latLng } from 'leaflet';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-home-map',
    templateUrl: './home-map.component.html',
    styleUrls: ['./home-map.component.css']
})
export class HomeMapComponent implements OnInit {
    map: Map;
    json;
    options = {
        layers: [
            tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' }),
        ],
        zoom: 4,
        center: latLng(37.6, -95.665)
    };

    constructor(private http: HttpClient) { }


    ngOnInit() {
    }

    onMapReady(map: Map) {
        this.http.get('assets/florida.json').subscribe((json: any) => {
            console.log(json);
            this.json = json;
            geoJSON(this.json).addTo(map);
        });
        this.http.get('assets/utah.json').subscribe((json: any) => {
            console.log(json);
            this.json = json;
            geoJSON(this.json).addTo(map);
        });
        this.http.get('assets/westVirginia.json').subscribe((json: any) => {
            console.log(json);
            this.json = json;
            geoJSON(this.json).addTo(map);
        });
    }
}
