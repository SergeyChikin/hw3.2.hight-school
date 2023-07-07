CREATE TABLE runner
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(25) NOT NULL ,
    age INT NOT NULL DEFAULT 18,
    have_licence BOOLEAN,
    car_number INT REFERENCES cars(id)
);

CREATE TABLE cars
(
    id SERIAL PRIMARY KEY ,
    manufactured VARCHAR(25) NOT NULL ,
    model VARCHAR(25) NOT NULL ,
    price NUMERIC(12, 2)
);