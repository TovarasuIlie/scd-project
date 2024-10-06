CREATE TABLE IF NOT EXISTS couriers (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    manager_id int(11) DEFAULT NULL
)