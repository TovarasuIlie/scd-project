import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DeliveryService } from '../../services/delivery.service';

@Component({
  selector: 'app-add-package-page',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './add-package-page.component.html',
  styleUrl: './add-package-page.component.css'
})
export class AddPackagePageComponent implements OnInit {
  addForm: FormGroup = new FormGroup({});
  errorMessages: string[] = [];

  constructor(private fb: FormBuilder, private deliveryService: DeliveryService) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.addForm = this.fb.group({
      pickingAddress: [null, [Validators.required]],
      price: [null, [Validators.required]],
      packages: this.fb.array([])
    })
  }

  get packages() {
    return this.addForm.get("packages") as FormArray;
  }

  deleteItem(index: number) {
    this.packages.removeAt(index);
  }

  addItem() {
    this.packages.push(this.fb.group({
      deliveryAddress: [null, [Validators.required]],
      payOnDelivery: ["false", [Validators.required]],
    }));
  }

  add() {
    this.errorMessages = [];
    if(this.addForm.valid) {
      if((this.addForm.get('packages') as FormArray).length >= 1) {
        this.deliveryService.addNewOrder(this.addForm.value).subscribe({
          next: (value) => {
            this.addForm.reset();
          },
          error: (err) => {
            console.log(err);
          }
        });
      } else {
        this.errorMessages.push("Trebuie sa adaugi minim un pachet!");
      }
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!");
    }
  }
}