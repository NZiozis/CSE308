import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Phase0Component } from './phase0.component';

describe('Phase0Component', () => {
  let component: Phase0Component;
  let fixture: ComponentFixture<Phase0Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Phase0Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Phase0Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
