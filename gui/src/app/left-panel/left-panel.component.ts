import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from '../services/map.service';
import {Precinct} from '../model/precinct.model';

@Component({
    selector: 'app-left-panel',
    templateUrl: './left-panel.component.html',
    styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {

    private REST_API_SERVER_URL = 'http://localhost:8080';
    private selectedRacePrecincts: Array<Precinct>;
    private precinctsObtained: boolean;
    displayedColumns: string[] = ['county', 'demographic'];


    constructor(private http: HttpClient, private mapService: MapService) {
    }

    ngOnInit() {
    }

}

