import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { Order } from 'src/app/models/order';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {
  orderForm: FormGroup;
  orderId!: number;
  order!: Order;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private orderService: OrderService,
    private router: Router
  ) {
    this.orderForm = this.fb.group({
      customerName: ['', Validators.required],
      orderDate: ['', Validators.required],
      totalAmount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.orderId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadOrder();
  }

  loadOrder() {
    this.order = this.orderService.getOrderById(this.orderId);
    if (this.order) {
      this.orderForm.patchValue({
        customerName: this.order.customerName,
        orderDate: this.order.orderDate,
        totalAmount: this.order.totalAmount
      });
    }
  }

  onSubmit() {
    if (this.orderForm.valid) {
      console.log('Order Updated:', this.orderForm.value);
      // Update order logic
      this.router.navigate(['/orders']);
    }
  }
}
