import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {

  private REST_API_SERVER = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  test(){
    const temp = this.httpClient.get(this.REST_API_SERVER);
  }

  ngOnInit() {
  }

}
