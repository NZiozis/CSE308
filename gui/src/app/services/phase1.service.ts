import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Phase1Config} from '../phase1/phase1.component';
import {GeoJSON} from 'leaflet';

@Injectable({
    providedIn: 'root'
})
export class Phase1Service {

    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase1JSON) {
        console.log(phase1JSON);
        this.http.post<Phase1Config>(
            this.mapService.REST_API_SERVER_URL + '/phase1', JSON.stringify(phase1JSON)).subscribe((json: any) => {
            const clusters = Object.keys(json.map);
            let colorIndex = 0;
            for (const cluster of clusters) {
                for (const precinct of json.map[cluster]) {
                    const currentLayer: GeoJSON<any> = this.mapService.precinctToLayerMapper.get(precinct);
                    currentLayer.setStyle({fillColor: this.mapService.colorSchemes[0][colorIndex % 6], fillOpacity: .5});
                }
                colorIndex += 1;
            }
        });
    }
}
