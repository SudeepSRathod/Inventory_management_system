import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { OrderService } from './order.service';
import { Order } from '../models/order';

describe('OrderService', () => {
  let service: OrderService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OrderService]
    });
    service = TestBed.inject(OrderService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch orders', () => {
    const dummyOrders: Order[] = [{ id: '1', customerName: 'John Doe', totalAmount: 100, orderDate: new Date() }];

    service.getOrders().subscribe(orders => {
      expect(orders.length).toBe(1);
      expect(orders[0].customerName).toBe('John Doe');
    });

    const req = httpMock.expectOne('http://localhost:3000/api/orders');
    expect(req.request.method).toBe('GET');
    req.flush(dummyOrders);
  });
});
