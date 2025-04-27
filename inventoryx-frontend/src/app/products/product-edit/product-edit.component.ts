import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {
  productForm: FormGroup;
  productId!: number;
  product!: Product;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private productService: ProductService
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProduct();
  }

  loadProduct() {
    this.product = this.productService.getProductById(this.productId);
    if (this.product) {
      this.productForm.patchValue({
        name: this.product.name,
        price: this.product.price,
        description: this.product.description
      });
    }
  }

  onSubmit() {
    if (this.productForm.valid) {
      console.log('Updated Product:', this.productForm.value);
      // Update product API
    }
  }
}
