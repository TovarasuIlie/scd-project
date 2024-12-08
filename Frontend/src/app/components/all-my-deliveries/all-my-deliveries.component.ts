import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Delivery } from '../../models/delivery';
import { DeliveryService } from '../../services/delivery.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-all-my-deliveries',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './all-my-deliveries.component.html',
  styleUrl: './all-my-deliveries.component.css'
})
export class AllMyDeliveriesComponent implements OnInit {
  email!: string;
  deliveries: Delivery[] = [];
  form: FormGroup = new FormGroup({});
  errorMessage!: string;

  constructor(private deliveryService: DeliveryService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initializeForm()
  }

  initializeForm() {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.email]]
    })
  }

  find() {
    this.errorMessage = "";
    if(this.form.valid) {
      this.email = this.form.value.email;
      this.deliveryService.getAllDeliveries(this.email).subscribe({
        next: (value) => {
          this.deliveries = value;
        }
      })
    } else {
      this.errorMessage = "Toate campurile trebuie completate!";
    }
  }
}
