import { TestBed } from '@angular/core/testing';

import { Phase0Service } from './phase0.service';

describe('Phase0Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: Phase0Service = TestBed.get(Phase0Service);
    expect(service).toBeTruthy();
  });
});
