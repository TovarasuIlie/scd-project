import { Courier } from "./courier";
import { AddPackage, EditPackage, Package } from "./package";

export interface Delivery {
    awb: string;
    pickingAddress: string;
    email: string;
    phoneNumber: string;
    packageSet: Package[];
    courier: Courier;
    createDate: string;
    specialKey: string;
    status: string;
    price: number;
}

export interface AddDelivery {
    pickingAddress: string;
    email: string;
    phoneNumber: string;
    price: number;
    packages: AddPackage[];
}

export interface EditDelivery {
    awb: string;
    pickingAddress: string;
    phoneNumber: string;
    price: number;
    packageSet: EditPackage[];
    specialKey: string;
}