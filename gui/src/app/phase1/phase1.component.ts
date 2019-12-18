import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';
import {Phase1Service} from '../services/phase1.service';

@Component({
    selector: 'app-phase1',
    templateUrl: './phase1.component.html',
    styleUrls: ['./phase1.component.css']
})
export class Phase1Component implements OnInit {

    private numberOfDistricts: number;
    private minorityThreshold: number;
    private majorityThreshold: number;
    private selectedRaces;
    private fullRun;
    private selectedColorScheme: number;
    private potentialColorSchemes = [{name: 'Scheme 1', value: 1}, {name: 'Scheme 2', value: 2}];

    constructor(private mapService: MapService, private phase1Service: Phase1Service) {
    }

    ngOnInit() {
        this.numberOfDistricts = 0;
        this.minorityThreshold = 50;
        this.majorityThreshold = 50;
        this.selectedRaces = [];
        this.fullRun = false;
    }

    createPhase1JSON() {
        const output = new Phase1Config();
        output.numberOfDistricts = this.numberOfDistricts;
        output.minorityThreshold = this.minorityThreshold;
        output.selectedRaces = this.selectedRaces;
        output.majorityThreshold = this.majorityThreshold;
        output.fullRun = this.fullRun;

        return output;
    }

    schemeChange(event) {
        if (event.isUserInput) {
            this.mapService.selectedColorScheme = event.source.value;
        }
    }

}

export class Phase1Config {
    numberOfDistricts: number;
    minorityThreshold: number;
    majorityThreshold: number;
    selectedRaces: Array<string>;
    fullRun: boolean;
}
