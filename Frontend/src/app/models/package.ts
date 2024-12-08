export interface Package {
    id: number,
    createdOn: string,
    deliveryAddress: string,
    phoneNumber: string;
    payOnDelivery: boolean,
    status: string
}

export interface AddPackage {
    deliveryAddress: string;
    phoneNumber: string;
    payOnDelivery: boolean;
}

export interface EditPackage {
    id: number;
    deliveryAddress: string;
    phoneNumber: string;
}