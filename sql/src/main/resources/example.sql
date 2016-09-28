CREATE DATABASE ACP14;

CREATE TABLE addresses(
  id INT PRIMARY KEY ,
  city VARCHAR (20),
  street VARCHAR (20),
  house_num INT
);

CREATE TABLE students(
  name VARCHAR(20) ,
  mail VARCHAR (30) UNIQUE NOT NULL ,
  age INT,
  birth DATE NOT NULL ,
  salary  DOUBLE ,
  address_id INT,
  FOREIGN KEY (address_id) REFERENCES addresses(id)
);

INSERT INTO students(name, birth, salary, address) VALUES ('Ivan', NOW(), 3000.00, 'Kyiv');

INSERT INTO students(name, birth, salary, address_id) VALUES ('Ivan', NOW(), 3000.00, 1);

SELECT * FROM students;

SELECT name, birth FROM students;

INSERT INTO addresses(id, city) VALUES (1, 'Kyiv');
