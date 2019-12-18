import { TestBed } from '@angular/core/testing';

import { Phase2Service } from './phase2.service';

describe('Phase2Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: Phase2Service = TestBed.get(Phase2Service);
    expect(service).toBeTruthy();
  });
});
