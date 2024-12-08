import { Component } from '@angular/core';
import { Delivery } from '../../models/delivery';
import { ActivatedRoute, Router } from '@angular/router';
import { DeliveryService } from '../../services/delivery.service';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-delivery-page',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './update-delivery-page.component.html',
  styleUrl: './update-delivery-page.component.css'
})
export class UpdateDeliveryPageComponent {
  delivery!: Delivery;
  errorMessage!: string;
  errorMessages: string[] = [];
  editForm: FormGroup = new FormGroup({});

  constructor(private activedRoute: ActivatedRoute, private deliveryService: DeliveryService, private fb: FormBuilder, private router: Router) {
    this.initializeOrder();
  }

  ngOnInit(): void {
    this.initializeOrder();
  }

  initializeOrder() {
    this.deliveryService.getOrderByID(this.activedRoute.snapshot.params["awb"]).subscribe({
      next: (delivery) => {
        this.delivery = delivery;
        this.initializeForm();
      },
      error: (response) => {
        this.errorMessage = "Nu a fost gasit!";
      }
    })
  }

  initializeForm() {
    this.editForm = this.fb.group({
      awb: [this.delivery.awb],
      pickingAddress: [this.delivery.pickingAddress, [Validators.required]],
      phoneNumber: [this.delivery.phoneNumber, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      price: [this.delivery.price, [Validators.required]],
      specialKey: [null, [Validators.required]],
      packages: this.fb.array([])
    })
    this.addItem();
  }

  addItem() {
    this.delivery.packageSet.forEach(element => {
      this.packages.push(this.fb.group({
        id: [element.id],
        deliveryAddress: [element.deliveryAddress, [Validators.required]],
        phoneNumber: [element.phoneNumber, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      }));
    });
  }

  get packages() {
    return this.editForm.get("packages") as FormArray;
  }

  editDelivery() {
    this.errorMessages = [];
    if(this.editForm.valid) {
      this.deliveryService.editDelivery(this.editForm.value).subscribe({
        next: (value) => {
          this.router.navigateByUrl("/vezi-coletul/" + value.awb);
          console.log(value);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages.push(err.error.message);
        }
      });
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!");
    }
  }
}
