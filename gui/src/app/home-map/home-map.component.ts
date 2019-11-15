import {Component, OnInit} from '@angular/core';
import {geoJSON, latLng, Map, tileLayer} from 'leaflet';
import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-home-map',
    templateUrl: './home-map.component.html',
    styleUrls: ['./home-map.component.css']
})
export class HomeMapComponent implements OnInit {
    map: Map;
    json;
    // Sets the base location to the United States
    options = {
        layers: [
            tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' }),
        ],
        zoom: 4,
        center: latLng(37.6, -95.665)
    };

    constructor(private http: HttpClient) {
    }


    ngOnInit() {
    }

    onMapReady(map: Map) {
        //On click zooms in on
        function onEachFeature(feature, layer) {
            layer.on('click', function(e) {
                map.fitBounds(e.layer.getBounds())
            });

        }

        // Loads in Florida
        this.http.get('assets/florida.json').subscribe((json: any) => {
            geoJSON(
                json,
                { onEachFeature: onEachFeature }
            ).addTo(map);
        });
        this.http.get('assets/utah.json').subscribe((json: any) => {
            geoJSON(
                json,
                { onEachFeature: onEachFeature }
            ).addTo(map);
        });
        this.http.get('assets/westVirginia.json').subscribe((json: any) => {
            geoJSON(
                json,
                { onEachFeature: onEachFeature }
            ).addTo(map);
        });
    }

}
