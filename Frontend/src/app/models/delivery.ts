import { AddPackage, Package } from "./package";

export interface Delivery {
    awb: string,
    pickingAddress: string,
    packageSet: Package[],
    createDate: string,
    price: number
}

export interface AddDelivery {
    pickingAddress: string;
    price: number;
    packages: AddPackage[];
}