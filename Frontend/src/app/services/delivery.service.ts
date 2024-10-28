import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddDelivery, Delivery } from '../models/delivery';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})

export class DeliveryService {

  constructor(private http: HttpClient) { }

  getOrderByID(awb: string) {
    return this.http.get<Delivery>(environment.API_KEY + "api/Delivery/get-delivery/" + awb);
  }

  addNewOrder(newDelivery: AddDelivery) {
    return this.http.post(environment.API_KEY + "api/Delivery/insert-new-delivery", newDelivery);
  }

  deleteOrder(awb: string) {
    return this.http.delete(environment.API_KEY + "api/Delivery/delete-delivery/" + awb);
  }
}
