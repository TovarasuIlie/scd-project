import { Component, OnInit } from '@angular/core';
import { Delivery } from '../../models/delivery';
import { DeliveryService } from '../../services/delivery.service';
import { Courier } from '../../models/courier';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-all-couriers',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './all-couriers.component.html',
  styleUrl: './all-couriers.component.css'
})
export class AllCouriersComponent implements OnInit {
  couriers: Courier[] = [];

  constructor(private deliveryService: DeliveryService) {

  }

  ngOnInit(): void {
    this.initializeCouriers();
  }

  initializeCouriers() {
    this.deliveryService.getAllCouriers().subscribe({
      next: (value) => {
        this.couriers = value;
      }
    })
  }
}
