import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Phase0TableComponent } from './phase0-table.component';

describe('Phase0TableComponent', () => {
  let component: Phase0TableComponent;
  let fixture: ComponentFixture<Phase0TableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Phase0TableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Phase0TableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
