import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';

@Component({
    selector: 'app-basic',
    templateUrl: './basic.component.html',
    styleUrls: ['./basic.component.css']
})
export class BasicComponent implements OnInit {

    private toggleLayer: boolean;

    constructor(private mapService: MapService) {
    }

    ngOnInit() {
        this.toggleLayer = false;
    }

}
