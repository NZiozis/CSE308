import {Control, DomUtil} from 'leaflet';
import {MapService} from '../services/map.service';

export class District extends Control {
    demographics: any;
    geography: string;
    geoId: number;
    districtNumber: number;
    mapService: MapService;

    constructor(opts, district, mapService: MapService) {
        super(opts);
        this.demographics = district.demographics;
        this.geography = district.geography;
        this.geoId = district.geoId;
        this.districtNumber = district.districtNumber;
        this.mapService = mapService;
    }

    onAdd(map) {
        const table = DomUtil.create('table') as HTMLTableElement;
        table.bgColor = '#303030';
        const row0 = table.insertRow(0);
        const cell0 = row0.insertCell(0);
        cell0.innerText = this.demographics.total;
        //
        // element.innerText = 'Total Pop: ' + this.demographics.total + '   White Population: ' + this.demographics.white;
        // element.innerText += '  % White: ' + ((this.demographics.white / this.demographics.total) * 100).toFixed(2);
        // element.style.color = 'black';

        return table;
    }
}
