import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Phase1Config} from '../phase1/phase1.component';

@Injectable({
    providedIn: 'root'
})
export class Phase1Service {

    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase1JSON) {
        console.log(phase1JSON);
        this.http.post<Phase1Config>(
            this.mapService.REST_API_SERVER_URL + '/phase1', JSON.stringify(phase1JSON)).subscribe((array: any) => {
            // TODO highlight the returned precincts and put them into the chart.
            // this.selectedRacePrecincts = array[0].precincts;
            console.log(array);
            // console.log(array[0]);
            // console.log(this.selectedRacePrecincts);
        });
    }
}
