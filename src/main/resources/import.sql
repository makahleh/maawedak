use mawedak;

INSERT INTO role (role_id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO department (department_id, department_name) VALUES (1, 'أطفال'), (2, 'رجال'), (3, 'نساء');

INSERT INTO payment_method (payment_method_id, name) VALUES (1, 'كاش'), (2, 'تأمين'), (3, 'بكج');

INSERT INTO treatment_method (treatment_method_id, name) VALUES
(1, 'العلاج بالبرودة'),
(2, 'العلاج بالشمع'),
(3, 'العلاج بالحرارة'),
(4, 'العلاج الكهربائي'),
(5, 'العلاج بالماء'),
(6, 'تمارين علاجية');

INSERT INTO expense_category (category_id, name) VALUES (1, 'إيجار'), (2, 'رواتب'), (3, 'معدات'), (4, 'أخرى');

-- CREATE TEST ACCOUNTS
INSERT INTO customer (customer_id, name, join_date, updated_date) VALUES (1, 'test', '2025-01-01 10:00:00', '2025-01-01 12:00:00');


INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (1, N'نات هيلث', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (2, N'ميديكسا', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (3, N'ميد نت', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (4, N'اومني كير', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (5, N'نيوتن للتأمين', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (6, N'نقابة المحامين', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (7, N'هيلث برو', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (8, N'غلوب مد', 10.0, 10.0);
INSERT INTO insurance (insurance_id, name, percentage, session_price) VALUES (9, N'نقابة اطباء الاسنان', 10.0, 10.0);

