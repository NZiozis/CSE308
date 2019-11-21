import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatPaginator} from '@angular/material';
import {MapService} from '../map.service';

@Component({
    selector: 'app-left-panel',
    templateUrl: './left-panel.component.html',
    styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {

    private REST_API_SERVER_URL = 'http://localhost:8080';
    private majorityPercentage;
    private votingPercentage;
    private selectedRaces;
    private possibleRaces = ['WHITE', 'BLACK', 'LATINO', 'ASIAN', 'PACIFIC_ISLANDER', 'NATIVE_AMERICAN'];
    private selectedRacePrecincts: Array<Precinct>;
    private precinctsObtained: boolean;
    displayedColumns: string[] = ['county', 'demographic'];


    constructor(private http: HttpClient, private mapService: MapService) {
        this.majorityPercentage = 50;
        this.votingPercentage = 50;
        this.selectedRaces = [];
    }

    phase0() {
        const phase0JSON = this.createPhase0JSON();
        console.log(phase0JSON);
        this.http.post<Config>(this.REST_API_SERVER_URL + '/phase0', JSON.stringify(phase0JSON)).subscribe((array: any) => {
            this.selectedRacePrecincts = array[0].precincts;
            console.log(array[0]);
            console.log(this.selectedRacePrecincts);
        });
        // this.http.get(this.REST_API_SERVER + '/temp').subscribe((data: Config) => {
        //     console.log(data.test);
        // });
    }

    createPhase0JSON() {
        const output = new Config();
        output.majorityPercentage = this.majorityPercentage;
        output.votingPercentage = this.votingPercentage;
        output.selectedRaces = this.selectedRaces;

        return output;
    }

    ngOnInit() {
    }

}

export class Config {
    votingPercentage: string;
    majorityPercentage: string;
    selectedRaces: Array<string>;
}

class Precinct {
    county: string;
    geoId: string;
    demographics: string;
    votingSet: string;
    demographicBloc: string;
    partyBloc: string;
    geography: string;
    neighbor: string;
}
