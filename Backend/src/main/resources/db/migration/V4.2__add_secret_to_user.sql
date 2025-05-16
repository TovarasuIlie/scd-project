ALTER TABLE couriers
ADD COLUMN secret_key varchar(255) DEFAULT NULL,
ADD COLUMN mfa_enable bit(1) DEFAULT b'0';
