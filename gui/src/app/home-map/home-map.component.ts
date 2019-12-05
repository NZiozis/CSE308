import {Component, OnInit} from '@angular/core';
import {geoJSON, LatLng, Map, TileLayer} from 'leaflet';
import {MapService} from '../map.service';

@Component({
    selector: 'app-home-map',
    templateUrl: './home-map.component.html',
    styleUrls: ['./home-map.component.css']
})
export class HomeMapComponent implements OnInit {
    // Sets the base location to the United States
    private options: { center: LatLng; layers: TileLayer[]; zoom: number };

    constructor(private mapService: MapService) {
    }

    ngOnInit() {
        this.options = this.mapService.getOptions();
    }

    // tslint:disable-next-line:no-shadowed-variable
    onMapReady(map: Map) {
        // On click zooms in on
        const self = this;

        function onEachFeature(feature, layer) {
            layer.on('click', event => {
                const stateName = self.mapService.getNameOfLayer(self.mapService.getLayerId(event.layer));
                self.mapService.setSelectedState(stateName);
            });
        }

        // Loads in Florida
        this.mapService.getFlorida().subscribe((json: any) => {
            const floridaGJson = geoJSON(json, {onEachFeature});
            floridaGJson.addTo(map);
            this.mapService.addLayerIdAndName(this.mapService.getGeoJsonId(floridaGJson), 'FLORIDA');
            this.mapService.addNameAndLayer('FLORIDA', this.mapService.getGeoJsonLayer(floridaGJson));
        });
        // Loads in Utah
        this.mapService.getUtah().subscribe((json: any) => {
            const utahGJson = geoJSON(json, {onEachFeature});
            utahGJson.addTo(map);
            this.mapService.addLayerIdAndName(this.mapService.getGeoJsonId(utahGJson), 'UTAH');
            this.mapService.addNameAndLayer('UTAH', this.mapService.getGeoJsonLayer(utahGJson));
        });
        // Loads in West Virginia
        this.mapService.getWestVirginiaGeoJson().subscribe((json: any) => {
            const westVirginiaGJson = geoJSON(json, {onEachFeature});
            westVirginiaGJson.addTo(map);
            this.mapService.addLayerIdAndName(this.mapService.getGeoJsonId(westVirginiaGJson), 'WEST_VIRGINIA');
            this.mapService.addNameAndLayer('WEST_VIRGINIA', this.mapService.getGeoJsonLayer(westVirginiaGJson));
        });
        this.mapService.setMap(map);
    }

}
