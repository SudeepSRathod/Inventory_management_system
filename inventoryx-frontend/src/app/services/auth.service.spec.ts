import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login user', () => {
    const dummyResponse = { token: '12345' };
    service.login({ email: 'test@test.com', password: '123456' }).subscribe(response => {
      expect(response.token).toEqual('12345');
    });

    const req = httpMock.expectOne('http://localhost:3000/api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(dummyResponse);
  });

  it('should register user', () => {
    const dummyResponse = { success: true };
    service.register({ name: 'Test', email: 'test@test.com', password: '123456' }).subscribe(response => {
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('http://localhost:3000/api/auth/register');
    expect(req.request.method).toBe('POST');
    req.flush(dummyResponse);
  });
});
