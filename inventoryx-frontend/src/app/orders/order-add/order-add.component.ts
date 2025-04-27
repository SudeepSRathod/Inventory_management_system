import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from 'src/app/services/order.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {
  orderForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private orderService: OrderService,
    private router: Router
  ) {
    this.orderForm = this.fb.group({
      customerName: ['', Validators.required],
      orderDate: ['', Validators.required],
      totalAmount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.orderForm.valid) {
      console.log('Order Added:', this.orderForm.value);
      this.orderService.addOrder(this.orderForm.value);
      this.router.navigate(['/orders']);
    }
  }
}
