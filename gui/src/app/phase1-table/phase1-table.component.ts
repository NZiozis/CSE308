import {Component, OnInit} from '@angular/core';
import {Phase1Service} from '../services/phase1.service';

@Component({
    selector: 'app-phase1-table',
    templateUrl: './phase1-table.component.html',
    styleUrls: ['./phase1-table.component.css']
})
export class Phase1TableComponent implements OnInit {

    private displayedColumns = ['number', 'white', 'africanAmerican', 'americanIndian', 'asian', 'other', 'pacificIslander', 'total'];

    constructor(private phase1Service: Phase1Service) {
    }

    ngOnInit() {
    }

}
