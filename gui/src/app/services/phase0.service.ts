import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Config} from '../phase0/phase0.component';

@Injectable({
    providedIn: 'root'
})
export class Phase0Service {
    public majorityPercentage;
    public votingPercentage;
    public selectedRaces;
    public selectedElection;

    constructor(private http: HttpClient, private mapService: MapService) {
            }

    run(phase0JSON) {
        console.log(phase0JSON);
        this.http.post<Config>(this.mapService.REST_API_SERVER_URL + '/phase0', JSON.stringify(phase0JSON)).subscribe((array: any) => {
            // TODO highlight the returned precincts and put them into the chart.
            // this.selectedRacePrecincts = array[0].precincts;
            console.log(array);
            // console.log(array[0]);
            // console.log(this.selectedRacePrecincts);
        });
    }
}
