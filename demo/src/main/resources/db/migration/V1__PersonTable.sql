CREATE TABLE person (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender INT,
    religion VARCHAR(100) NOT NULL,
    masters_deg BOOLEAN
);
