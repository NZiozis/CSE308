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
    private displayedColumns = ['race', 'population', 'percent'];
    private votingColumns = ['party', 'votes', 'percent', 'numRepresentatives', 'percentRepresentatives'];

    constructor(private mapService: MapService) {
    }

    getRequiredInformation() {
        this.mapService.getStateInfo().subscribe((json: State) => {
                this.state = json;
                console.log(json);
                this.populateDemArray();
                this.populateVotingArray();
            }
        );
    }

    generatePercent(value, total) {
        return (value / total * 100).toFixed(2);
    }

    populateDemArray() {
        const output: any[] = [];
        const total = this.state.demographicContext.total;
        output.push(
            {
                name: 'A. American',
                population: this.state.demographicContext.africanAmerican,
                percent: this.generatePercent(this.state.demographicContext.africanAmerican, total)
            },
            {
                name: 'White',
                population: this.state.demographicContext.white,
                percent: this.generatePercent(this.state.demographicContext.white, total)
            },
            {
                name: 'A. Indian',
                population: this.state.demographicContext.americanIndian,
                percent: this.generatePercent(this.state.demographicContext.americanIndian, total)
            },
            {
                name: 'Asian',
                population: this.state.demographicContext.asian,
                percent: this.generatePercent(this.state.demographicContext.asian, total)
            },
            {
                name: 'P. Islander',
                population: this.state.demographicContext.pacificIslander,
                percent: this.generatePercent(this.state.demographicContext.pacificIslander, total)
            },
            {
                name: 'Hispanic',
                population: this.state.demographicContext.other,
                percent: this.generatePercent(this.state.demographicContext.other, total)
            },
            {name: 'Total', population: this.state.demographicContext.total, percent: '100.00'},
        );
        this.state.arrayDemographicContext = output;
    }

    populateVotingArray() {
        const output: any[] = [];
        const data = this.state.votingSet.filter(x => x.election === this.mapService.selectedElection);
        const republican = data.filter(x => x.party === 'REPUBLICAN')[0];
        const democrat = data.filter(x => x.party === 'DEMOCRAT')[0];
        const total = republican.votes + democrat.votes;
        const totalReps = this.state.incumbentData.republican + this.state.incumbentData.democrat;
        output.push(
            {
                name: 'Republican',
                votes: republican.votes,
                percent: this.generatePercent(republican.votes, total),
                numRepresentatives: this.state.incumbentData.republican,
                percentRepresentatives: this.generatePercent(this.state.incumbentData.republican, totalReps)
            },
            {
                name: 'Democrat',
                votes: democrat.votes,
                percent: this.generatePercent(democrat.votes, total),
                numRepresentatives: this.state.incumbentData.democrat,
                percentRepresentatives: this.generatePercent(this.state.incumbentData.democrat, totalReps)
            },
            {
                name: 'Total',
                votes: total,
                percent: '100.00',
                numRepresentatives: totalReps,
                percentRepresentatives: '100.00'
            }
        );
        this.state.arrayVotingSet = output;
    }

    ngOnInit() {
    }

}
