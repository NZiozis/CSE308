import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';
import {State} from '../model/state.model';

@Component({
    selector: 'app-state-info',
    templateUrl: './state-info.component.html',
    styleUrls: ['./state-info.component.css']
})
export class StateInfoComponent implements OnInit {

    private state: State;
    private displayedColumns = ['race', 'population'];

    constructor(private mapService: MapService) {
    }

    getRequiredInformation() {
        this.mapService.getStateInfo().subscribe((json: State) => {
                this.state = json;
                console.log(json);
                this.populateDemArray();
            }
        );
    }

    populateDemArray() {
        const output: any[] = [];
        output.push(
            {name: 'A. American', population: this.state.demographicContext.africanAmerican},
            {name: 'White', population: this.state.demographicContext.white},
            {name: 'A. Indian', population: this.state.demographicContext.americanIndian},
            {name: 'Asian', population: this.state.demographicContext.asian},
            {name: 'P. Islander', population: this.state.demographicContext.pacificIslander},
            {name: 'Other', population: this.state.demographicContext.other},
            {name: 'Total', population: this.state.demographicContext.total},
        );
        this.state.arrayDemographicContext = output;
    }

    ngOnInit() {
    }

}
