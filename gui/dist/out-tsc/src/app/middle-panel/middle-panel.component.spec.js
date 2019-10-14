import { async, TestBed } from '@angular/core/testing';
import { MiddlePanelComponent } from './middle-panel.component';
describe('MiddlePanelComponent', () => {
    let component;
    let fixture;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MiddlePanelComponent]
        })
            .compileComponents();
    }));
    beforeEach(() => {
        fixture = TestBed.createComponent(MiddlePanelComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
//# sourceMappingURL=middle-panel.component.spec.js.map