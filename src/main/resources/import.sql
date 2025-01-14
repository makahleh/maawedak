use mawedak;
INSERT INTO Customer (customer_id, name, join_date, updated_date) VALUES (1, 'mak', '2025-01-01 10:00:00', '2025-01-01 12:00:00');

INSERT INTO role (id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO department (department_id, department_name) VALUES (1, 'Children'), (2, 'Male'), (3, 'Female');

INSERT INTO payment_method (payment_method_id, name) VALUES (1, 'Cash'), (2, 'Insurance'), (3, 'Percentage');

INSERT INTO Insurance (insurance_id, name, percentage) VALUES (1, 'HealthCare Plus', 80.0), (2, 'Premium Shield', 90.0);
