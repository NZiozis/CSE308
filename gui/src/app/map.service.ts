import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LatLng, latLng, TileLayer, tileLayer} from 'leaflet';
import {Observable} from 'rxjs';
import {Config} from './left-panel/left-panel.component';

@Injectable({
    providedIn: 'root'
})
export class MapService {

    private mapUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    private REST_API_SERVER_URL = 'http://localhost:8080';
    private options: { center: LatLng; layers: TileLayer[]; zoom: number };
    private map;
    private layerToNameMapper = new Map<string, string>();
    private selectedState: string;
    public stateIsSelected: boolean;

    constructor(private http: HttpClient) {
        this.stateIsSelected = false;
    }

    getOptions() {
        this.options = {
            layers: [
                tileLayer(this.mapUrl, {maxZoom: 18, attribution: '...'}),
            ],
            zoom: 4,
            center: latLng(37.6, -95.665)
        };
        return this.options;
    }

    setSelectedState(selectedState: string): void {
        this.selectedState = selectedState;
        this.stateIsSelected = true;
        this.http.post<Config>(this.REST_API_SERVER_URL + '/setState', selectedState).subscribe();
    }

    setMap(map) {
        this.map = map;
    }

    getLayerId(layer) {
        return (layer as any)._leaflet_id;
    }

    getGeoJsonId(geoJson) {
        const temp = Object.getOwnPropertyNames((geoJson as any)._layers);
        const temp2 = Object.getOwnPropertyNames((geoJson as any)._layers[temp[0]]._layers);
        return (geoJson as any)._layers[temp[0]]._layers[temp2[0]]._leaflet_id;
    }

    addNameAndLayer(layer, name) {
        this.layerToNameMapper.set(layer, name);
    }

    getNameOfLayer(layer) {
        return this.layerToNameMapper.get(layer);
    }

    getFlorida(): Observable<any> {
        return this.http.get('assets/florida.json');
    }

    getUtah(): Observable<any> {
        return this.http.get('assets/utah.json');
    }

    getWestVirginiaGeoJson(): Observable<any> {
        // return this.http.get('assets/westVirginia.json');
        return this.http.get(this.REST_API_SERVER_URL + '/westVirginia');
    }
}
