import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormControl} from '@angular/forms';

@Component({
    selector: 'app-left-panel',
    templateUrl: './left-panel.component.html',
    styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {

    private REST_API_SERVER = 'http://localhost:8080';
    private majorityPercentage;
    private selectedRaces;
    private possibleRaces = ["Asian", "Hispanic", "Afriacn American","Pacific Islander","Native American"];

    constructor(private http: HttpClient) {
        this.majorityPercentage = 50;
        this.selectedRaces = [];
    }

    phase0() {
        const phase0JSON = this.createPhase0JSON(this.majorityPercentage, this.selectedRaces);
        console.log(phase0JSON);
        this.http.post<Config>(this.REST_API_SERVER + '/phase0', JSON.stringify(phase0JSON)).subscribe();
        // this.http.get(this.REST_API_SERVER + '/temp').subscribe((data: Config) => {
        //     console.log(data.test);
        // });
    }

    createPhase0JSON(majPercentage : String, races : Array<String>){
        let output = {};
        output["majorityPercentage"] = majPercentage;
        output["races"] = races;
        return output;
    }

    ngOnInit() {
    }

}

export interface Config {
    test: string;
}
