import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthGuardService } from './auth-guard.service';
import { AuthService } from './auth.service';

describe('AuthGuardService', () => {
  let service: AuthGuardService;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['isLoggedIn']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        AuthGuardService,
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    service = TestBed.inject(AuthGuardService);
  });

  it('should allow access if user is logged in', () => {
    authServiceSpy.isLoggedIn.and.returnValue(true);
    expect(service.canActivate()).toBeTrue();
  });

  it('should redirect to login if user is not logged in', () => {
    authServiceSpy.isLoggedIn.and.returnValue(false);
    expect(service.canActivate()).toBeFalse();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/auth/login']);
  });
});
