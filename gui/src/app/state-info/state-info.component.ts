import {Component, OnInit} from '@angular/core';
import {MapService} from '../services/map.service';

@Component({
    selector: 'app-state-info',
    templateUrl: './state-info.component.html',
    styleUrls: ['./state-info.component.css']
})
export class StateInfoComponent implements OnInit {

    private state;

    constructor(private mapService: MapService) {
    }

    ngOnInit() {
    }

}
