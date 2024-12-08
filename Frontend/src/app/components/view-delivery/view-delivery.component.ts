import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DeliveryService } from '../../services/delivery.service';
import { Delivery } from '../../models/delivery';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-order',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './view-delivery.component.html',
  styleUrl: './view-delivery.component.css'
})
export class ViewDeliveryComponent implements OnInit {
  delivery!: Delivery;
  errorMessage!: string;

  constructor(private activedRoute: ActivatedRoute, private deliveryService: DeliveryService) {
    this.initializeOrder();
  }

  ngOnInit(): void {
    this.initializeOrder();
  }

  initializeOrder() {
    this.deliveryService.getOrderByID(this.activedRoute.snapshot.params["awb"]).subscribe({
      next: (delivery) => {
        this.delivery = delivery;
      },
      error: (response) => {
        this.errorMessage = "Nu a fost gasit!";
      }
    })
  }

}
