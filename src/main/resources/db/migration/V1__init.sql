CREATE TABLE IF NOT EXISTS employeelevels
(
    id SERIAL CONSTRAINT employeelevels_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);
