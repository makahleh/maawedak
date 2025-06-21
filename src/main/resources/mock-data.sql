use mawedak;

INSERT INTO therapist (therapist_id, customer_id, name, phone_number, username, password, department_id, hiring_date, updated_date, created_date, is_active, percentage)
VALUES
(1, 1, 'محمد مكاحله', '00962775569227', 'makahleh', '$2a$10$cL3UO3dFgUdsccbILTO4we0uE9j3xewhOMES5UaYgTL2H2rAUD/5q', 1, '2025-01-01', '2025-01-01 10:00:00', '2025-01-01 10:00:00', TRUE, 0.10),
(2, 1, 'محمد أحمد', '0791234561', 'mohammad.ahmad', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 2, '2023-01-10', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(3, 1, 'خالد يوسف', '0791234562', 'khaled.yousef', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 2, '2023-02-15', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(4, 1, 'سارة علي', '0791234563', 'sara.ali', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 3, '2023-03-20', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(5, 1, 'ليلى حسن', '0791234564', 'laila.hassan', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 3, '2023-04-25', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(6, 1, 'أحمد سمير', '0791234565', 'ahmad.samir', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 2, '2023-05-30', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(7, 1, 'منى عادل', '0791234566', 'mona.adel', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 3, '2023-06-05', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(8, 1, 'جمال فؤاد', '0791234567', 'jamal.fouad', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 2, '2023-07-12', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(9, 1, 'هند مصطفى', '0791234568', 'hind.mostafa', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 3, '2023-08-18', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(10, 1, 'طارق نبيل', '0791234569', 'tariq.nabil', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 1, '2023-09-25', CURRENT_DATE, CURRENT_DATE, true, 0.10),
(11, 1, 'إيمان زكي', '0791234570', 'iman.zaki', '$2a$10$N9qo8uLOickgx2ZMRZo5i.Sz2b9xRkK4O/JloXrA4/n9UqUogkYyq', 1, '2023-10-30', CURRENT_DATE, CURRENT_DATE, true, 0.10);


INSERT INTO therapists_roles (therapist_id, role_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1);

INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (1, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (2, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (3, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (4, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (5, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (6, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (7, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (8, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (9, 1);


INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (1, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (2, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (3, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (4, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (5, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (6, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (7, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (8, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (9, 1);