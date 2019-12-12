import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';

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

    constructor(private mapService: MapService) {
    }

    test() {
        console.log(this);
    }

    ngOnInit() {
        this.numberOfDistricts = 0;
        this.minorityThreshold = 50;
        this.majorityThreshold = 50;
        this.selectedRaces = [];
        this.fullRun = false;
    }

}
