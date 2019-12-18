import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';
import {Phase2Service} from '../services/phase2.service';

@Component({
    selector: 'app-phase2',
    templateUrl: './phase2.component.html',
    styleUrls: ['./phase2.component.css']
})
export class Phase2Component implements OnInit {

    private config: Phase2Config;

    constructor(private mapService: MapService, private phase2Service: Phase2Service) {
        this.config = new Phase2Config();
        this.config.partisanFairness = this.config.reockCompactness = this.config.convexHullCompactness = 0.5;
        this.config.edgeCompactness = this.config.efficiencyGap = this.config.populationEquality = this.config.competitiveness = 0.5;
        this.config.populationHomogeneity = 0.5;
    }

    ngOnInit() {
    }

    createPhase2Json() {
        const output = new Phase2Config();
        output.partisanFairness = this.config.partisanFairness;
        output.reockCompactness = this.config.reockCompactness;
        output.convexHullCompactness = this.config.convexHullCompactness;
        output.edgeCompactness = this.config.edgeCompactness;
        output.efficiencyGap = this.config.efficiencyGap;
        output.populationEquality = this.config.populationEquality;
        output.competitiveness = this.config.competitiveness;
        output.populationHomogeneity = this.config.populationHomogeneity;

        return output;
    }

}

export class Phase2Config {
    partisanFairness: number;
    reockCompactness: number;
    convexHullCompactness: number;
    edgeCompactness: number;
    efficiencyGap: number;
    populationEquality: number;
    competitiveness: number;
    populationHomogeneity: number;
}
