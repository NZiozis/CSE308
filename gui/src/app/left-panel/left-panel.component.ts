import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

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
    private possibleRaces = ['Asian', 'Hispanic', 'Afriacn American', 'Pacific Islander', 'Native American'];

    constructor(private http: HttpClient) {
        this.majorityPercentage = 50;
        this.selectedRaces = [];
    }

    phase0() {
        const phase0JSON = this.createPhase0JSON();
        console.log(phase0JSON);
        this.http.post<Config>(this.REST_API_SERVER_URL + '/phase0', JSON.stringify(phase0JSON)).subscribe();
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
