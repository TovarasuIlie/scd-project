CREATE TABLE IF NOT EXISTS packages (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    courier_id int(11) DEFAULT NULL,
    created_on datetime(6) DEFAULT CURRENT_TIMESTAMP,
    delivery_address varchar(155) NOT NULL,
    pay_on_delivery bit(1) DEFAULT b'0',
    status enum('NEW', 'PENDING', 'DELIVERED') DEFAULT 'NEW'
)