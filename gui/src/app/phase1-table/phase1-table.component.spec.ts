import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Phase1TableComponent } from './phase1-table.component';

describe('Phase1TableComponent', () => {
  let component: Phase1TableComponent;
  let fixture: ComponentFixture<Phase1TableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Phase1TableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Phase1TableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
