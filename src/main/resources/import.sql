use mawedak;

INSERT INTO role (role_id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO department (department_id, department_name) VALUES (1, 'أطفال'), (2, 'رجال'), (3, 'نساء');

INSERT INTO payment_method (payment_method_id, name) VALUES (1, 'كاش'), (2, 'تأمين'), (3, 'نسبة');

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

INSERT INTO insurance (insurance_id,customer_id, name, percentage, session_price) VALUES (1,1, 'HealthCare Plus', 80.0, 10,0), (2,1, 'Premium Shield', 90.0, 10,0);


