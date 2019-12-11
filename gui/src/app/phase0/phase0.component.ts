import {Component, OnInit} from '@angular/core';
import {Phase0Service} from '../services/phase0.service';
import {MapService} from '../services/map.service';


@Component({
    selector: 'app-phase0',
    templateUrl: './phase0.component.html',
    styleUrls: ['./phase0.component.css']
})
export class Phase0Component implements OnInit {

    public majorityPercentage;
    public votingPercentage;
    public selectedRaces;
    public selectedElection;

    constructor(private phase0Service: Phase0Service, private mapService: MapService) {
        this.majorityPercentage = 50;
        this.votingPercentage = 50;
        this.selectedRaces = [];
    }

    createPhase0JSON() {
        const output = new Config();
        output.majorityPercentage = this.majorityPercentage;
        output.votingPercentage = this.votingPercentage;
        output.selectedRaces = this.selectedRaces;
        output.selectedElection = this.selectedElection;
        this.updatePhase0Service();

        return output;
    }

    updatePhase0Service() {
        this.phase0Service.votingPercentage = this.votingPercentage;
        this.phase0Service.majorityPercentage = this.majorityPercentage;
        this.phase0Service.selectedRaces = this.selectedRaces;
        this.phase0Service.selectedElection = this.selectedElection;
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
