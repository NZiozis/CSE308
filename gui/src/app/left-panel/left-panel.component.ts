import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
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
    private selectedElection;
    private selectedRacePrecincts: Array<Precinct>;
    private precinctsObtained: boolean;
    displayedColumns: string[] = ['county', 'demographic'];


    constructor(private http: HttpClient, private mapService: MapService) {
        this.majorityPercentage = 50;
        this.votingPercentage = 50;
        this.selectedRaces = [];
    }

    test() {
        const start = Date.now();
        this.http.get(this.REST_API_SERVER_URL + '/getState').subscribe((data) => {
                console.log(data);
                console.log(Date.now() - start);
            }
        );
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
        output.selectedElection = this.selectedElection;

        return output;
    }

    ngOnInit() {
    }

}

export class Config {
    votingPercentage: string;
    majorityPercentage: string;
    selectedElection: string;
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
