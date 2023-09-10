import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarLinkComponent } from './navbar-link.component';

describe('NavbarLinkComponent', () => {
  let component: NavbarLinkComponent;
  let fixture: ComponentFixture<NavbarLinkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarLinkComponent]
    });
    fixture = TestBed.createComponent(NavbarLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
