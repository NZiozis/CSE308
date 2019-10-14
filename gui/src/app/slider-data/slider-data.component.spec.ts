import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SliderDataComponent } from './slider-data.component';

describe('SliderDataComponent', () => {
  let component: SliderDataComponent;
  let fixture: ComponentFixture<SliderDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SliderDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SliderDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
