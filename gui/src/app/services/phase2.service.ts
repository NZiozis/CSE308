import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Phase2Config} from '../phase2/phase2.component';
import {GeoJSON} from 'leaflet';

@Injectable({
    providedIn: 'root'
})
export class Phase2Service {
    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase2Json) {
        console.log(phase2Json);
        this.http.post<Phase2Config>(
            this.mapService.REST_API_SERVER_URL + '/phase2', JSON.stringify(phase2Json)).subscribe((json: any) => {
                let colorIndex = 0;
                const clusters = json.array;
                for (const cluster of clusters) {
                    for (const precinct of cluster.value1) {
                        const currentLayer: GeoJSON<any> = this.mapService.precinctToLayerMapper.get(precinct);
                        currentLayer.setStyle({fillColor: this.mapService.colorSchemes[0][colorIndex % 6], fillOpacity: .5});
                    }
                    colorIndex += 1;
                }
            }
        );
    }
}
