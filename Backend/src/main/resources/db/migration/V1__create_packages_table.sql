CREATE TABLE IF NOT EXISTS packages (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_on datetime(6) DEFAULT CURRENT_TIMESTAMP,
    delivery_address varchar(255) NOT NULL,
    pay_on_delivery bit(1) DEFAULT b'0',
    phone_number varchar(10) NOT NULL,
    status enum('NEW', 'PENDING', 'DELIVERED') DEFAULT 'NEW'
)