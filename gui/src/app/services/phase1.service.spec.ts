import { TestBed } from '@angular/core/testing';

import { Phase1Service } from './phase1.service';

describe('Phase1Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: Phase1Service = TestBed.get(Phase1Service);
    expect(service).toBeTruthy();
  });
});
