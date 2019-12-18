import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Config} from '../phase0/phase0.component';
import {GeoJSON} from 'leaflet';

@Injectable({
    providedIn: 'root'
})
export class Phase0Service {
    public majorityPercentage;
    public votingPercentage;
    public selectedRaces;
    public selectedElection;
    public votingBlocs;
    public demographicBlocs;

    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase0JSON) {
        console.log(phase0JSON);
        this.mapService.precinctToLayerMapper.forEach((value: GeoJSON, key: string) => {
            value.setStyle({fillColor: '#ff15ed'});
        });
        this.http.post<Config>(this.mapService.REST_API_SERVER_URL + '/phase0', JSON.stringify(phase0JSON)).subscribe((array: any) => {
            // TODO highlight the returned precincts and put them into the chart.
            // this.selectedRacePrecincts = array[0].precincts;
            const demographicPrecincts = array[0].precincts;
            const votingPrecincts = array[1].precincts;
            for (const precinct of votingPrecincts) {
                const layer: GeoJSON = this.mapService.precinctToLayerMapper.get(precinct.geoId);
                layer.setStyle({fillColor: '#eff000'});
            }
            this.demographicBlocs = demographicPrecincts;
            this.votingBlocs = votingPrecincts;
            console.log(array);
            // console.log(array[0]);
            // console.log(this.selectedRacePrecincts);
        });
    }
}
