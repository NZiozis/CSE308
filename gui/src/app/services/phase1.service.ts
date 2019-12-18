import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Phase1Config} from '../phase1/phase1.component';
import {GeoJSON} from 'leaflet';

@Injectable({
    providedIn: 'root'
})
export class Phase1Service {

    public calculatedClusters = [];

    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase1JSON) {
        console.log(phase1JSON);
        this.http.post<Phase1Config>(
            this.mapService.REST_API_SERVER_URL + '/phase1', JSON.stringify(phase1JSON)).subscribe((json: any) => {
            let colorIndex = 0;
            console.log(json);
            console.log('Phase 1 complete');
            const clusters = json.array;
            const tempClusters = [];
            for (const cluster of clusters) {
                tempClusters.push(cluster.value0);
                const color = this.mapService.colorSchemes[0][colorIndex % 6];
                for (const precinct of cluster.value1) {
                    const currentLayer: GeoJSON<any> = this.mapService.precinctToLayerMapper.get(precinct);
                    currentLayer.setStyle({fillColor: color, fillOpacity: .5});
                }
                colorIndex += 1;
            }
            this.calculatedClusters = tempClusters;
        });
    }
}
