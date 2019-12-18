import {Control, DomUtil} from 'leaflet';

export class District extends Control {
    demographics: any;
    geography: string;
    geoId: number;
    districtNumber: number;
    votingSet: any;
    context: any;
    incumbent: any;

    constructor(opts, district: District, context) {
        super(opts);
        this.demographics = district.demographics;
        this.votingSet = district.votingSet;
        this.geography = district.geography;
        this.geoId = district.geoId;
        this.districtNumber = district.districtNumber;
        this.context = context;
        this.incumbent = district.incumbent;
    }

    onAdd(map) {
        const table = DomUtil.create('table') as HTMLTableElement;
        table.bgColor = '#303030';
        table.style.fontSize = 'large';
        if (this.context.showElection) {
            const data = this.votingSet.filter(x => x.election === this.context.selectedElection);
            const republican = data.filter(x => x.party === 'REPUBLICAN')[0];
            const democrat = data.filter(x => x.party === 'DEMOCRAT')[0];
            const totalVotes = republican.votes + democrat.votes;

            const row0 = table.insertRow(0);
            const cell00 = row0.insertCell(0);
            const cell01 = row0.insertCell(1);
            const cell02 = row0.insertCell(2);
            const cell03 = row0.insertCell(3);
            const cell04 = row0.insertCell(4);
            cell00.innerText = 'Party';
            cell01.innerText = '  ';
            cell02.innerText = 'Votes';
            cell03.innerText = '  ';
            cell04.innerText = 'Vote %';

            const row1 = table.insertRow(1);
            const cell10 = row1.insertCell(0);
            const cell11 = row1.insertCell(1);
            const cell12 = row1.insertCell(2);
            const cell13 = row1.insertCell(3);
            const cell14 = row1.insertCell(4);
            cell10.innerText = 'Republican';
            cell11.innerText = '  ';
            cell12.innerText = (republican.votes);
            cell13.innerText = '  ';
            cell14.innerText = (republican.votes / totalVotes * 100).toFixed(2);

            const row2 = table.insertRow(2);
            const cell20 = row2.insertCell(0);
            const cell21 = row2.insertCell(1);
            const cell22 = row2.insertCell(2);
            const cell23 = row2.insertCell(3);
            const cell24 = row2.insertCell(4);
            cell20.innerText = 'Democrat';
            cell21.innerText = '  ';
            cell22.innerText = (democrat.votes);
            cell23.innerText = '  ';
            cell24.innerText = (democrat.votes / totalVotes * 100).toFixed(2);

            const row3 = table.insertRow(3);
            const cell30 = row3.insertCell(0);
            const cell31 = row3.insertCell(1);
            const cell32 = row3.insertCell(2);
            const cell33 = row3.insertCell(3);
            const cell34 = row3.insertCell(4);
            cell30.innerText = 'Total';
            cell31.innerText = '  ';
            cell32.innerText = (totalVotes);
            cell33.innerText = '  ';
            cell34.innerText = '100';

            const row4 = table.insertRow(4);
            const cell40 = row4.insertCell(0);
            const cell41 = row4.insertCell(1);
            const cell42 = row4.insertCell(2);
            // const cell43 = row3.insertCell(3);
            // const cell44 = row3.insertCell(4);
            cell40.innerText = 'Incumbent';
            cell41.innerText = '  ';
            cell42.innerText = this.incumbent;

        } else {
            const row0 = table.insertRow(0);
            const cell00 = row0.insertCell(0);
            const cell01 = row0.insertCell(1);
            const cell02 = row0.insertCell(2);
            const cell03 = row0.insertCell(3);
            cell01.innerText = 'Pop #';
            cell02.innerText = '  ';
            cell03.innerText = 'Pop %';

            const row1 = table.insertRow(1);
            const cell10 = row1.insertCell(0);
            const cell11 = row1.insertCell(1);
            const cell12 = row1.insertCell(2);
            const cell13 = row1.insertCell(3);
            cell10.innerText = 'White';
            cell11.innerText = this.demographics.white;
            cell12.innerText = '  ';
            cell13.innerText = (this.demographics.white / this.demographics.total * 100).toFixed(2);

            const row2 = table.insertRow(2);
            const cell20 = row2.insertCell(0);
            const cell21 = row2.insertCell(1);
            const cell22 = row2.insertCell(2);
            const cell23 = row2.insertCell(3);
            cell20.innerText = 'A. American';
            cell21.innerText = this.demographics.africanAmerican;
            cell22.innerText = '  ';
            cell23.innerText = (this.demographics.africanAmerican / this.demographics.total * 100).toFixed(2);

            const row3 = table.insertRow(3);
            const cell30 = row3.insertCell(0);
            const cell31 = row3.insertCell(1);
            const cell32 = row3.insertCell(2);
            const cell33 = row3.insertCell(3);
            cell30.innerText = 'A. Indian';
            cell31.innerText = this.demographics.americanIndian;
            cell32.innerText = '  ';
            cell33.innerText = (this.demographics.americanIndian / this.demographics.total * 100).toFixed(2);

            const row4 = table.insertRow(4);
            const cell40 = row4.insertCell(0);
            const cell41 = row4.insertCell(1);
            const cell42 = row4.insertCell(2);
            const cell43 = row4.insertCell(3);
            cell40.innerText = 'Asian';
            cell41.innerText = this.demographics.asian;
            cell42.innerText = '  ';
            cell43.innerText = (this.demographics.asian / this.demographics.total * 100).toFixed(2);

            const row5 = table.insertRow(5);
            const cell50 = row5.insertCell(0);
            const cell51 = row5.insertCell(1);
            const cell52 = row5.insertCell(2);
            const cell53 = row5.insertCell(3);
            cell50.innerText = 'P. Islander';
            cell51.innerText = this.demographics.pacificIslander;
            cell52.innerText = '  ';
            cell53.innerText = (this.demographics.pacificIslander / this.demographics.total * 100).toFixed(2);

            const row6 = table.insertRow(6);
            const cell60 = row6.insertCell(0);
            const cell61 = row6.insertCell(1);
            const cell62 = row6.insertCell(2);
            const cell63 = row6.insertCell(3);
            cell60.innerText = 'Other';
            cell61.innerText = this.demographics.other;
            cell62.innerText = '  ';
            cell63.innerText = (this.demographics.other / this.demographics.total * 100).toFixed(2);

            const row7 = table.insertRow(7);
            const cell70 = row7.insertCell(0);
            const cell71 = row7.insertCell(1);
            const cell72 = row7.insertCell(2);
            const cell73 = row7.insertCell(3);
            cell70.innerText = 'Total';
            cell71.innerText = this.demographics.total;
            cell72.innerText = '  ';
            cell73.innerText = '100.00';
        }


        return table;
    }
}
