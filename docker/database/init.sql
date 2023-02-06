# Create schema for database
DROP DATABASE IF EXISTS db_webshop;
CREATE DATABASE IF NOT EXISTS db_webshop CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

# Mark which schema to use
USE db_webshop;

# Set default timezone
SET time_zone = "+00:00";

# Create table account
DROP TABLE IF EXISTS account_rules;
CREATE TABLE account_rules (
    db_id               BIGINT              NOT NULL AUTO_INCREMENT,
    db_create_on        TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_update_on        TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_name             VARCHAR(255)        DEFAULT NULL,
    db_email            VARCHAR(255)        DEFAULT NULL,
    db_input_currency   VARCHAR(255)        DEFAULT NULL,
    db_output_currency  VARCHAR(255)        DEFAULT NULL,
    db_country          VARCHAR(255)        DEFAULT NULL,
    CONSTRAINT pk_account_rules_db_id PRIMARY KEY account_rules(db_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# Create indexes for table account
CREATE INDEX idx_db_webshop_account_rules_db_create_on ON account_rules(db_create_on) using btree;
CREATE INDEX idx_db_webshop_account_rules_db_update_on ON account_rules(db_update_on) using btree;

# Create table country_rules
DROP TABLE IF EXISTS country_rules;
CREATE TABLE country_rules (
    db_id              BIGINT              NOT NULL AUTO_INCREMENT,
    db_create_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_update_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_country         VARCHAR(255)        DEFAULT NULL,
    CONSTRAINT pk_country_rules_db_id PRIMARY KEY country_rules(db_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# Create indexes for table country_rules
CREATE INDEX idx_db_webshop_country_rules_db_create_on ON country_rules(db_create_on) using btree;
CREATE INDEX idx_db_webshop_country_rules_db_update_on ON country_rules(db_update_on) using btree;

# Create table currency_rules
DROP TABLE IF EXISTS currency_rules;
CREATE TABLE currency_rules (
    db_id              BIGINT              NOT NULL AUTO_INCREMENT,
    db_create_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_update_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_currency        VARCHAR(255)        DEFAULT NULL,
    db_rate            DOUBLE              DEFAULT NULL,
    CONSTRAINT pk_currency_rules_db_id PRIMARY KEY currency_rules(db_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# Create indexes for table country_rules
CREATE INDEX idx_db_webshop_currency_rules_db_create_on ON currency_rules(db_create_on) using btree;
CREATE INDEX idx_db_webshop_currency_rules_db_update_on ON currency_rules(db_update_on) using btree;

# Create table type_rules
DROP TABLE IF EXISTS type_rules;
CREATE TABLE type_rules (
    db_id              BIGINT              NOT NULL AUTO_INCREMENT,
    db_create_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_update_on       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_type            VARCHAR(255)        DEFAULT NULL,
    CONSTRAINT pk_type_rules_db_id PRIMARY KEY type_rules(db_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# Create indexes for table country_rules
CREATE INDEX idx_db_webshop_type_rules_db_create_on ON type_rules(db_create_on) using btree;
CREATE INDEX idx_db_webshop_type_rules_db_update_on ON type_rules(db_update_on) using btree;

# Create table vat_rules
DROP TABLE IF EXISTS vat_rules;
CREATE TABLE vat_rules (
    db_id              BIGINT               NOT NULL AUTO_INCREMENT,
    db_create_on       TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_update_on       TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    db_area            VARCHAR(255)         DEFAULT NULL,
    db_rate            DOUBLE               DEFAULT NULL,
    db_country_id      BIGINT               DEFAULT NULL,
    db_type_id         BIGINT               DEFAULT NULL,
    CONSTRAINT pk_vat_rules_db_id PRIMARY KEY type_rules(db_id),
    CONSTRAINT fk_vat_rules_db_country_id FOREIGN KEY vat_rules(db_country_id) REFERENCES country_rules(db_id),
    CONSTRAINT fk_vat_rules_db_type_id FOREIGN KEY vat_rules(db_type_id) REFERENCES type_rules(db_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# Create indexes for table country_rules
CREATE INDEX idx_db_webshop_vat_rules_db_create_on ON vat_rules(db_create_on) using btree;
CREATE INDEX idx_db_webshop_vat_rules_db_update_on ON vat_rules(db_update_on) using btree;

# Insert data to tables
INSERT INTO account_rules(db_name, db_email, db_input_currency, db_output_currency, db_country) VALUES ('user', 'user@gmail.com', 'DKK', 'DKK', 'DK');
INSERT INTO country_rules(db_country) VALUES ('DK'),('NO'),('SE'),('GB'),('DE');
INSERT INTO currency_rules(db_currency, db_rate) VALUES ('DKK', 100),('NOK', 73.50),('SEK', 70.23),('GBP', 891.07),('EUR', 743.93);
INSERT INTO type_rules(db_type) VALUES ('ALL'),('ONLINE'),('BOOK');
INSERT INTO vat_rules(db_area, db_rate, db_country_id, db_type_id) VALUES ('Scandinavia', 25, 1, 1),
                                                                          ('Scandinavia', 25, 2, 1),
                                                                          ('Scandinavia', 25, 3, 1),
                                                                          ('Great Britain', 20, 4, 1),
                                                                          ('Germany', 19, 5, 2),
                                                                          ('Germany', 12, 5, 3);

# Log below are available in docker container log
SHOW CREATE DATABASE db_database;
SHOW CREATE TABLE account_rules;
SHOW CREATE TABLE country_rules;
SHOW CREATE TABLE currency_rules;
SHOW CREATE TABLE type_rules;
SHOW CREATE TABLE vat_rules;
SELECT @@global.time_zone;
SELECT @@session.time_zone;

SELECT v.db_area, v.db_rate, c.db_country, t.db_type FROM vat_rules as v
    INNER JOIN country_rules as c ON v.db_country_id = c.db_id
    INNER JOIN type_rules as t ON v.db_type_id = t.db_id
         WHERE c.db_country = 'DK' AND t.db_type = 'ALL';
