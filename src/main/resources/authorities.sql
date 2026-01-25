CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255),
    funds DOUBLE DEFAULT 0,
	cart TEXT NULL,
    wishlist TEXT,
    reviewRates FLOAT,
    sellingProds TEXT NULL,
    prodReviewed TEXT NULL,
    is_vendor tinyint ,
    enabled tinyint NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE authorities (
    username VARCHAR(255) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

#CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

INSERT INTO users
(username, password, email, phone, funds, cart, wishlist, reviewRates, sellingProds, prodReviewed, is_vendor, enabled)
VALUES
("Karim", "{noop}81258136", "karimahmad@gmail.com", "81258136", 999999.99, "[]", "[]", 0.0, "[]", "[]", 0, 1);
INSERT INTO authorities
VALUES
("Karim","ROLE_ADMIN");