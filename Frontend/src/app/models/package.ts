export interface Package {
    id: number,
    createdOn: string,
    deliveryAddress: string,
    payOnDelivery: boolean,
    status: string
}

export interface AddPackage {
    deliveryAddress: string;
    payOnDelivery: boolean;
}