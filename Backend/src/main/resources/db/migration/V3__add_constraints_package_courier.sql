ALTER TABLE couriers
    ADD CONSTRAINT FK_manager_id FOREIGN KEY (manager_id) REFERENCES couriers(id) ON DELETE SET NULL;