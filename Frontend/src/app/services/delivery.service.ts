import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddDelivery, Delivery, EditDelivery } from '../models/delivery';
import { environment } from '../../environments/environment.development';
import { Courier } from '../models/courier';

@Injectable({
  providedIn: 'root'
})

export class DeliveryService {

  constructor(private http: HttpClient) { }

  getOrderByID(awb: string) {
    return this.http.get<Delivery>(environment.API_KEY + "api/Delivery/get-delivery/" + awb);
  }

  addNewOrder(newDelivery: AddDelivery) {
    return this.http.post<Delivery>(environment.API_KEY + "api/Delivery/insert-new-delivery", newDelivery);
  }

  editDelivery(delivery: EditDelivery) {
    return this.http.put<Delivery>(environment.API_KEY + "api/Delivery/edit-delivery", delivery);
  }

  getAllDeliveries(email: string) {
    return this.http.get<Delivery[]>(environment.API_KEY + "api/Delivery/get-customer-deliveries/" + email);
  }

  getAllCouriers() {
    return this.http.get<Courier[]>(environment.API_KEY + "api/Courier/get-couriers");
  }

  getMyPendingDeliveries(email: string) {
    return this.http.get<Delivery[]>(environment.API_KEY + "api/Delivery/get-customer-pending-deliveries/" + email);
  }
}
