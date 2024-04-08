CREATE TABLE Grants (
    grant_id SERIAL,
    supplier_name VARCHAR(255),
    amount BIGINT,
	PRIMARY KEY (grant_id)
);

COPY Grants(grant_id, supplier_name, amount)
FROM 'PATH TO DATA SET HERE'
DELIMITER ','
CSV HEADER;