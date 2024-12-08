import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Delivery } from '../../models/delivery';
import { DeliveryService } from '../../services/delivery.service';

@Component({
  selector: 'app-all-my-pending-deliveries',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './all-my-pending-deliveries.component.html',
  styleUrl: './all-my-pending-deliveries.component.css'
})
export class AllMyPendingDeliveriesComponent {
  email!: string;
  deliveries: Delivery[] = [];
  form: FormGroup = new FormGroup({});
  errorMessage!: string;

  constructor(private deliveryService: DeliveryService, private fb: FormBuilder, private activatedRoute: ActivatedRoute) {
    this.email = this.activatedRoute.snapshot.params["email"];
  }

  ngOnInit(): void {
    this.initializeDeliveries()
  }

  initializeDeliveries() {
    this.deliveryService.getMyPendingDeliveries(this.email).subscribe({
      next: (value) => {
        this.deliveries = value;
      }
    })
  }
}
