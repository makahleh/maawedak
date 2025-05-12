use mawedak;

INSERT INTO role (role_id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO department (department_id, department_name) VALUES (1, 'أطفال'), (2, 'رجال'), (3, 'نساء');

INSERT INTO payment_method (payment_method_id, name) VALUES (1, 'كاش'), (2, 'تأمين'), (3, 'نسبة');


INSERT INTO expense_category (category_id, name) VALUES (1, 'إيجار'), (2, 'رواتب'), (3, 'معدات'), (4, 'أخرى');

-- CREATE TEST ACCOUNTS
INSERT INTO customer (customer_id, name, join_date, updated_date) VALUES (1, 'test', '2025-01-01 10:00:00', '2025-01-01 12:00:00');

INSERT INTO insurance (insurance_id,customer_id, name, percentage) VALUES (1,1, 'HealthCare Plus', 80.0), (2,1, 'Premium Shield', 90.0);

--INSERT INTO therapist (customer_id, name, phone_number, username, password, department_id, hiring_date, updated_date, created_date, is_active, percentage) VALUES (1, 'Mohammad Makahleh', '00962775569227', 'makahleh', '$2a$10$cL3UO3dFgUdsccbILTO4we0uE9j3xewhOMES5UaYgTL2H2rAUD/5q',1, '2025-01-01', '2025-01-01 10:00:00', '2025-01-01 10:00:00', TRUE, 0.10);

--INSERT INTO therapists_roles (therapist_id, role_id) VALUES (1, 1);
