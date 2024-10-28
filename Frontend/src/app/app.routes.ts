import { Routes } from '@angular/router';
import { IndexPageComponent } from './components/index-page/index-page.component';
import { AddPackagePageComponent } from './components/add-package-page/add-package-page.component';
import { ViewDeliveryComponent } from './components/view-delivery/view-delivery.component';

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
    }
];
