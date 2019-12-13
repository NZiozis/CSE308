import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GeoJSON, geoJSON, ImageOverlay, LatLng, latLng, Layer, LayerGroup, TileLayer, tileLayer} from 'leaflet';
import {Observable} from 'rxjs';
import {Backend} from '../model/backend.model';
import {Config} from '../phase0/phase0.component';
import {District} from '../model/district.model';
import {Precinct} from '../model/precinct.model';

@Injectable({
    providedIn: 'root'
})
export class MapService {

    constructor(private http: HttpClient) {
        this.stateIsSelected = false;
        this.states = [
            {name: 'West Virginia', backendName: 'WEST_VIRGINIA'},
            {name: 'Utah', backendName: 'UTAH'},
            {name: 'Florida', backendName: 'FLORIDA'}
        ];
        this.possibleRaces = [
            {name: 'White', backendName: 'WHITE'},
            {name: 'African American', backendName: 'BLACK'},
            {name: 'Latino', backendName: 'LATINO'},
            {name: 'Asian', backendName: 'ASIAN'},
            {name: 'Pacific Islander', backendName: 'PACIFIC_ISLANDER'},
            {name: 'Native American', backendName: 'NATIVE_AMERICAN'},
        ];
        this.elections = [
            {name: 'Presidential 2016', backendName: 'PRESIDENTIAL_2016'},
            {name: 'Congressional 2016', backendName: 'CONGRESSIONAL_2016'},
            {name: 'Congressional 2018', backendName: 'CONGRESSIONAL_2018'},
        ];

    }

    public map;
    public stateIsSelected: boolean;
    public state;
    public states: Backend[];
    public possibleRaces: Backend[];
    public elections: Backend[];
    public nameToLayerMapper = new Map<string, ImageOverlay>();
    public precinctToLayerMapper = new Map<string, GeoJSON<any>>();
    public selectedState: string;
    public REST_API_SERVER_URL = 'http://localhost:8080';

    private mapUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    private options: { center: LatLng; layers: TileLayer[]; zoom: number };
    private layerIdToNameMapper = new Map<string, string>();
    private currentInfo;
    private districtLayerGroup;
    public precinctLayerGroup;

    toggleDistrictPrecinctLayers(isDisplayDistricts: boolean) {
        if (!isDisplayDistricts) {
            this.map.removeLayer(this.districtLayerGroup);
            this.map.addLayer(this.precinctLayerGroup);
        } else {
            this.map.removeLayer(this.precinctLayerGroup);
            this.map.addLayer(this.districtLayerGroup);
        }
    }

    getOptions() {
        this.options = {
            layers: [
                tileLayer(this.mapUrl, {maxZoom: 18, attribution: '...'}),
            ],
            zoom: 5,
            center: latLng(37.6, -95.665)
        };
        return this.options;
    }

    getStateInfo() {
        console.log('test');
        this.http.get(this.REST_API_SERVER_URL + '/getState').subscribe(json => {
            console.log(json);
        });
    }

    setSelectedState(selectedState: string): void {
        if (this.selectedState !== selectedState) {

            this.selectedState = selectedState;
            this.stateIsSelected = true;

            this.map.fitBounds(this.nameToLayerMapper.get(selectedState).getBounds());
            const setState = this.http.post<Config>(this.REST_API_SERVER_URL + '/setState', selectedState);
            const self = this;
            setState.subscribe((json) => {
                // console.log('State' + json);
                this.getDistricts().subscribe((districts: District[]) => {
                    this.districtLayerGroup = new LayerGroup();
                    console.log(districts);
                    for (const district of districts) {
                        // console.log(district);
                        const geoJson = geoJSON(JSON.parse(district.geography), {
                            onEachFeature(feature, layer) {
                                layer.on('mouseover', function() {
                                    this.setStyle({
                                        fillColor: '#0000ff'
                                    });
                                    self.currentInfo = new District({position: 'bottomleft'}, district, this);
                                    self.currentInfo.addTo(self.map);
                                });
                                layer.on('mouseout', function() {
                                    this.setStyle({
                                        fillColor: '#ff0000'
                                    });
                                    self.map.removeControl(self.currentInfo);
                                });
                            }
                        });
                        geoJson.setStyle({fillColor: '#ff0000'});
                        self.districtLayerGroup.addLayer(geoJson);
                    }
                    this.districtLayerGroup.addTo(this.map);
                    console.log('Districts completed');
                });
                this.getPrecincts().subscribe((precincts: Precinct[]) => {
                    console.log(precincts);
                    this.precinctLayerGroup = new LayerGroup();
                    for (const precinct of precincts) {
                        const geoJson = geoJSON(JSON.parse(precinct.geography), {
                            onEachFeature(feature, layer) {
                                layer.on('mouseover', function() {
                                    this.setStyle({
                                        fillOpacity: 1
                                    });
                                    self.currentInfo = new District({position: 'bottomleft'}, precinct, this);
                                    self.currentInfo.addTo(self.map);
                                });
                                layer.on('mouseout', function() {
                                    this.setStyle({
                                        fillOpacity: 0.2
                                    });
                                    self.map.removeControl(self.currentInfo);
                                });
                            }
                        });
                        geoJson.setStyle({fillColor: '#ff15ed', weight: .5});
                        self.precinctLayerGroup.addLayer(geoJson);
                        self.precinctToLayerMapper.set(precinct.geoId, geoJson);
                    }
                    // this.precinctLayerGroup.addTo(this.map);
                    console.log('Precincts completed');
                });
            });
        }
    }

    setMap(map) {
        this.map = map;
    }

    /**
     * Gets the id of the given layer
     * @param layer:Layer The layer that you want to get the id of
     * @return Number     The number that is the leaflet_id
     */
    getLayerId(layer: Layer) {
        return (layer as any)._leaflet_id;
    }

    /**
     * Gets the Layer Id of the GeoJson created layer
     * @param  geoJson: any   The GeoJson Object generated by Leaflet
     *
     * @return Number        The number that is the leaflet_id
     */
    getGeoJsonId(geoJson) {
        const temp = Object.getOwnPropertyNames((geoJson as any)._layers);
        const temp2 = Object.getOwnPropertyNames((geoJson as any)._layers[temp[0]]._layers);
        return (geoJson as any)._layers[temp[0]]._layers[temp2[0]]._leaflet_id;
    }

    /**
     * Gets the layer buried in the GeoJson
     * @param  geoJson: any    The GeoJson Object generated by Leaflet
     * @return Layer          The buried layer
     */
    getGeoJsonLayer(geoJson) {
        const temp = Object.getOwnPropertyNames((geoJson as any)._layers);
        const temp2 = Object.getOwnPropertyNames((geoJson as any)._layers[temp[0]]._layers);
        return (geoJson as any)._layers[temp[0]]._layers[temp2[0]];
    }

    addLayerIdAndName(layerId, name) {
        this.layerIdToNameMapper.set(layerId, name);
    }

    addNameAndLayer(name, layer) {
        this.nameToLayerMapper.set(name, layer);
    }

    getNameOfLayer(layer) {
        return this.layerIdToNameMapper.get(layer);
    }

    getFlorida(): Observable<any> {
        return this.http.get(this.REST_API_SERVER_URL + '/florida');
    }

    getUtah(): Observable<any> {
        return this.http.get('assets/utah.json');
    }

    getWestVirginiaGeoJson(): Observable<any> {
        return this.http.get(this.REST_API_SERVER_URL + '/westVirginia');
    }

    getDistricts(): Observable<any> {
        return this.http.get(this.REST_API_SERVER_URL + '/getDistricts');
    }

    getPrecincts(): Observable<any> {
        return this.http.get(this.REST_API_SERVER_URL + '/getPrecincts');
    }
}
