import {Component, OnInit} from '@angular/core';
import {Phase0Service} from '../services/phase0.service';


export interface DemoData {
    demoGroup: string;
    position: number;
    percChosen: number;
    party: string;
    county;
    string;
    totalPop: number;
}

// const DEMO_DATA: DemoData[] = [
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456
//     },
// ];


export interface VotingData {
    demoGroup: string;
    pName: string;
    position: number;
    percChosen: number;
    party: string;
    county;
    string;
    totalPop: number;
}

// const VOTING_DATA: VotingData[] = [
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
//     {
//         position: 1,
//         demoGroup: 'African American',
//         county: 'Boward County',
//         pName: 'Beaver Creek',
//         percChosen: '42%',
//         party: 'Republican',
//         county: 'Brower',
//         totalPop: 3456567
//     },
// ];


@Component({
    selector: 'app-phase0-table',
    templateUrl: './phase0-table.component.html',
    styleUrls: ['./phase0-table.component.css']
})
export class Phase0TableComponent implements OnInit {

    displayedColumns: string[] = ['position', 'demoGroup', 'county', 'party', 'percChosen', 'totalPop'];


    constructor(private phase0Service: Phase0Service) {
    }

    converter(input: string) {
        let out = '';
        switch (input) {
            case 'BLACK':
                out = 'africanAmerican';
                break;
            case 'WHITE':
                out = 'white';
                break;
            case 'ASIAN':
                out = 'asian';
                break;
            case 'PACIFIC_ISLANDER':
                out = 'pacificIslander';
                break;
            default:
                out = '';
        }

        return out;
    }

    getPartyVotes(votingSet, party) {
        const election = votingSet.filter(data => data.party === party);
        return election[0].votes;
    }

    sumElectionVotes(votingSet) {
        let total = 0;
        for (const election of votingSet) {
            total += election.votes;
        }
        return total;
    }

    ngOnInit() {
    }

}
