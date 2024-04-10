CREATE TABLE Grants (
    grant_id SERIAL,
    supplier_name VARCHAR(255),
    amount BIGINT,
	PRIMARY KEY (grant_id)
);

CREATE TABLE Users (
    id SERIAL,
    fname VARCHAR(255) NOT NULL,
    lname VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);