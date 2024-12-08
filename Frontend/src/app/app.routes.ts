import { Routes } from '@angular/router';
import { IndexPageComponent } from './components/index-page/index-page.component';
import { AddPackagePageComponent } from './components/add-package-page/add-package-page.component';
import { ViewDeliveryComponent } from './components/view-delivery/view-delivery.component';
import { UpdateDeliveryPageComponent } from './components/update-delivery-page/update-delivery-page.component';
import { AllMyDeliveriesComponent } from './components/all-my-deliveries/all-my-deliveries.component';
import { AllMyPendingDeliveriesComponent } from './components/all-my-pending-deliveries/all-my-pending-deliveries.component';
import { AllCouriersComponent } from './components/all-couriers/all-couriers.component';

export const routes: Routes = [
    {
        path: "",
        component: IndexPageComponent
    },
    {
        path: "adauga-pachet",
        component: AddPackagePageComponent
    },
    {
        path: "vezi-coletul/:awb",
        component: ViewDeliveryComponent
    },
    {
        path: "editeza-coletul/:awb",
        component: UpdateDeliveryPageComponent
    },
    {
        path: "coletele-mele",
        component: AllMyDeliveriesComponent
    },
    {
        path: "coletele-mele-in-asteptare/:email",
        component: AllMyPendingDeliveriesComponent
    },
    {
        path: "courieri",
        component: AllCouriersComponent
    }
];
