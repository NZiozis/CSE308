import {Control, DomUtil} from 'leaflet';

export class District extends Control {
    demographics: any;
    geography: string;
    geoId: number;
    districtNumber: number;

    constructor(opts, district) {
        super(opts);
        this.demographics = district.demographics;
        this.geography = district.geography;
        this.geoId = district.geoId;
        this.districtNumber = district.districtNumber;
    }

    onAdd(map) {
        const element: any = DomUtil.create('h1');

        element.innerText = 'Total Pop: ' + this.demographics.total + '   White Population: ' + this.demographics.white;
        element.innerText += '  % White: ' + ((this.demographics.white / this.demographics.total) * 100).toFixed(2);
        element.style.color = 'black';

        return element;
    }
}
