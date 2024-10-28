CREATE TABLE IF NOT EXISTS orders
(
    awb varchar(16) NOT NULL PRIMARY KEY,
    courier_id int(11) DEFAULT NULL,
    picking_address varchar(255) NOT NULL,
    price decimal NOT NULL,
    create_date datetime(6) NOT NULL,
    CONSTRAINT FK_courier_id_orders FOREIGN KEY (courier_id) REFERENCES couriers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS order_package
(
    order_awb varchar(16) NOT NULL,
    package_id int(11) NOT NULL,
    PRIMARY KEY(order_awb, package_id),
    CONSTRAINT FK_order_awb_orders FOREIGN KEY (order_awb) REFERENCES orders(awb) ON DELETE CASCADE,
    CONSTRAINT FK_package_id_packages FOREIGN KEY (package_id) REFERENCES packages(id)
)