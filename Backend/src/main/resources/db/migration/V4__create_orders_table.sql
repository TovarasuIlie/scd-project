CREATE TABLE IF NOT EXISTS deliveries
(
    awb varchar(16) NOT NULL PRIMARY KEY,
    courier_id int(11) DEFAULT NULL,
    picking_address varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone_number varchar(10) NOT NULL,
    special_key varchar(266) UNIQUE NOT NULL,
    price decimal NOT NULL,
    create_date datetime(6) NOT NULL,
    CONSTRAINT FK_courier_id_deliveries FOREIGN KEY (courier_id) REFERENCES couriers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS delivery_packages
(
    delivery_awb varchar(16) NOT NULL,
    package_id int(11) NOT NULL,
    PRIMARY KEY(delivery_awb, package_id),
    CONSTRAINT FK_delivery_awb_deliveries FOREIGN KEY (delivery_awb) REFERENCES deliveries(awb) ON DELETE CASCADE,
    CONSTRAINT FK_package_id_packages FOREIGN KEY (package_id) REFERENCES packages(id) ON DELETE CASCADE
)