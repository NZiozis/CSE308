import {Component, OnInit} from '@angular/core';
import {MapService} from '../map.service';


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    constructor(private mapService: MapService) {
    }

    ngOnInit() {
    }

    onDropDownChange(event) {
        // The event fires twice. First to change to the new value and second to get rid of the old value.
        // Without this statement, the old value would override the new value. The entire method needs be contained in the if
        // statement unless you want to do something with the oldValue
        if (event.isUserInput) {
            const selectedState = event.source.value;
            this.mapService.setSelectedState(selectedState);
        }
    }

}
