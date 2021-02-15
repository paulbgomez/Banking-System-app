DROP SCHEMA IF EXISTS banking_app;
CREATE SCHEMA banking_app;
USE banking_app;

CREATE TABLE `user` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255),
  `password` VARCHAR(255),
  username VARCHAR(36) UNIQUE,
  PRIMARY KEY (id)
);


CREATE TABLE `role` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_role VARCHAR(255),
  user_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES `user` (id)
);


CREATE TABLE `admin` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES `user`(id)
);


CREATE TABLE account_holder (
  id BIGINT NOT NULL AUTO_INCREMENT,
  birth_date DATE NOT NULL,
  mailing_address_city VARCHAR(255),
  mailing_address_state VARCHAR(255),
  mailing_address_country VARCHAR(255),
  mailing_address_postal_code VARCHAR(255),
  mailing_address_direction VARCHAR(255),
  primary_address_city VARCHAR(255),
  primary_address_country VARCHAR(255),
  primary_address_state VARCHAR(255),
  primary_address_postal_code VARCHAR(255),
  primary_address_direction VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES `user` (id)
);


CREATE TABLE third_party (
  id BIGINT NOT NULL AUTO_INCREMENT,
  hash_key VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE `account` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  balance_amount DECIMAL(19,2),
  balance_currency VARCHAR(255),
  penalty_fee_amount DECIMAL(19,2),
  penalty_fee_currency VARCHAR(255),
  creation_time DATETIME,
  primary_owner_id BIGINT NOT NULL,
  secondary_owner_id BIGINT,
  is_frozen BOOLEAN,
  `status` VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (secondary_owner_id) REFERENCES account_holder (id),
  FOREIGN KEY (primary_owner_id) REFERENCES account_holder (id)
);

CREATE TABLE student_checking (
  id BIGINT NOT NULL AUTO_INCREMENT,
  secret_key VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES `account` (id)
);


CREATE TABLE checking (
  id BIGINT NOT NULL AUTO_INCREMENT,
  below_minimum_balance BOOLEAN NOT NULL,
  last_fee DATETIME,
  monthly_fee_amount DECIMAL(19,2),
  monthly_fee_currency VARCHAR(255),
  minimum_balance_amount DECIMAL(19,2),
  minimum_balance_currency VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES student_checking (id)
);


CREATE TABLE credit_card (
  id bigint NOT NULL AUTO_INCREMENT,
  credit_limit_amount DECIMAL(19,2),
  credit_limit_currency VARCHAR(255),
  interest_rate DECIMAL(19,2) NOT NULL,
  last_interest_update DATE,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES `account` (id)
);


CREATE TABLE savings (
  id BIGINT NOT NULL AUTO_INCREMENT,
  below_minimum_balance BOOLEAN NOT NULL,
  interest_rate DECIMAL(19,2) NOT NULL,
  last_fee DATETIME NOT NULL,
  minimum_balance_amount DECIMAL(19,2),
  minimum_balance_currency VARCHAR(255),
  secret_key VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES `account` (id)
);

CREATE TABLE movement (
  id BIGINT NOT NULL AUTO_INCREMENT,
  sender_account BIGINT,
  receiver_account BIGINT,
  transference_date DATETIME,
  quantity_amount DECIMAL(19,2),
  quantity_currency VARCHAR(255),
  concept VARCHAR(255),
  PRIMARY KEY (id),
  FOREIGN KEY (sender_account) REFERENCES account (id),
  FOREIGN KEY (receiver_account) REFERENCES account (id)
);

-- Pre-defined default admin username/password is admin/1234 (the password is hashed in the DB). Use it for testing purposes.
INSERT INTO user (id, name, password, username) VALUES (1, "NAME", "$2a$10$RFb9IXjhOrYv2OrjE5ru0OOv65OxN7NXB83abFOMfqENWw4Id.k3q", "admin");
INSERT INTO role (user_role, user_id) VALUES ("ADMIN", 1);
INSERT INTO admin (id) VALUES (1);
