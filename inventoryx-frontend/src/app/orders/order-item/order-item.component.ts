import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css']
})
export class OrderItemComponent implements OnInit {
  @Input() itemIndex!: number;
  @Output() removeItem = new EventEmitter<number>();

  itemForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.itemForm = this.fb.group({
      productId: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(1)]],
      price: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {}

  onRemove() {
    this.removeItem.emit(this.itemIndex);
  }
}
