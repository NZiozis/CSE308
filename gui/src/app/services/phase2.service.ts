import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';
import {Phase2Config} from '../phase2/phase2.component';

@Injectable({
    providedIn: 'root'
})
export class Phase2Service {
    constructor(private http: HttpClient, private mapService: MapService) {
    }

    run(phase2Json) {
        console.log(phase2Json);
        this.http.post<Phase2Config>(
            this.mapService.REST_API_SERVER_URL + '/phase2', JSON.stringify(phase2Json)).subscribe((array: any) => {
            console.log(array);
        });
    }
}
