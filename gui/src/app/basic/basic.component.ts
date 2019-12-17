import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';

@Component({
    selector: 'app-basic',
    templateUrl: './basic.component.html',
    styleUrls: ['./basic.component.css']
})
export class BasicComponent implements OnInit {

    private toggleLayer: boolean;
    private toggleElection: boolean;
    private selectedElection;

    constructor(private mapService: MapService) {
    }

    ngOnInit() {
        this.toggleLayer = false;
        this.mapService.showElection = this.toggleElection = false;
        this.mapService.selectedElection = this.selectedElection = 'CONGRESSIONAL_2016';
    }

    electionChange(event) {
        if (event.isUserInput) {
            const selectedElection = event.source.value;
            console.log(selectedElection);
            this.mapService.selectedElection = selectedElection;
        }
    }

}

