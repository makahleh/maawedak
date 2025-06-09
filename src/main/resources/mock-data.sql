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

INSERT INTO patient (
    patient_id, customer_id, name, phone_number, therapist_id, department_id, payment_method_id,
    insurance_id, session_price, number_of_total_sessions, notes, is_active, created_date,
    updated_date, age
)
VALUES
(1, 1, 'أحمد السعدي', '00962791000001', 3, 2, 1, NULL, 50.0, 0, 'يعاني من آلام الظهر', true, NOW(), NOW(), 35),
(2, 1, 'ليلى الخطيب', '00962791000002', 5, 3, 2, 1, 60.0, 0, 'مشاكل في التوازن', true, NOW(), NOW(), 42),
(3, 1, 'خالد العجارمة', '00962791000003', 2, 2, 1, NULL, 10.0, 0, 'إصابة رياضية بالركبة', true, NOW(), NOW(), 28),
(4, 1, 'نور الزعبي', '00962791000004', 7, 3, 2, 2, 20.0, 0, 'إجهاد في العمود الفقري', true, NOW(), NOW(), 33),
(5, 1, 'علي الدويري', '00962791000005', 4, 2, 1, NULL, 10.0, 0, 'تمزق في الأربطة', true, NOW(), NOW(), 31),
(6, 1, 'منى النجار', '00962791000006', 6, 3, 1, NULL, 20.0, 0, 'آلام مستمرة في الظهر', true, NOW(), NOW(), 40),
(7, 1, 'ياسر الشهاب', '00962791000007', 8, 3, 2, 1, 10.0, 0, 'مشاكل في المفاصل', true, NOW(), NOW(), 36),
(8, 1, 'هند الجراح', '00962791000008', 1, 3, 2, 2, 10.0, 0, 'إجهاد مزمن في العضلات', true, NOW(), NOW(), 27),
(9, 1, 'زياد الكردي', '00962791000009', 10, 2, 1, NULL, 10.0, 0, 'إصابة في الكتف', true, NOW(), NOW(), 39),
(10, 1, 'رنا الحياري', '00962791000010', 9, 3, 2, 1, 20.0, 0, 'تورم في الركبة', true, NOW(), NOW(), 34),
(11, 1, 'فارس العواملة', '00962791000011', 11, 2, 1, NULL, 10.0, 0, 'شد عضلي في الظهر', true, NOW(), NOW(), 38),
(12, 1, 'سلمى العمري', '00962791000012', 2, 3, 2, 2, 20.0, 0, 'تورم في القدم', true, NOW(), NOW(), 29),
(13, 1, 'مروان الجبور', '00962791000013', 4, 2, 1, NULL, 10.0, 0, 'مشاكل في العمود الفقري', true, NOW(), NOW(), 43),
(14, 1, 'ديما الخصاونة', '00962791000014', 3, 3, 1, NULL, 20.0, 0, 'آلام الرقبة المزمنة', true, NOW(), NOW(), 37),
(15, 1, 'هيثم العتوم', '00962791000015', 6, 2, 2, 1, 10.0, 0, 'إصابة رياضية بالكاحل', true, NOW(), NOW(), 30),
(16, 1, 'مها الرواشدة', '00962791000016', 5, 3, 1, NULL, 20.0, 0, 'مشاكل في العظام', true, NOW(), NOW(), 32),
(17, 1, 'خالد الحياري', '00962791000017', 7, 2, 2, 2, 10.0, 0, 'تمزق غضروفي في الركبة', true, NOW(), NOW(), 41),
(18, 1, 'عبير الفاعوري', '00962791000018', 8, 3, 1, NULL, 10.0, 0, 'إجهاد في الرقبة', true, NOW(), NOW(), 33),
(19, 1, 'رامي بني هاني', '00962791000019', 9, 1, 2, 1, 20.0, 0, 'آلام مزمنة في الظهر', true, NOW(), NOW(), 45),
(20, 1, 'وسام عضيبات', '00962791000020', 11, 1, 2, 2, 10.0, 0, 'إصابة في الكاحل', true, NOW(), NOW(), 36);

INSERT INTO session (customer_id, patient_id, therapist_id, payment_method_id, insurance_id, start_date_time, end_date_time, notes, payment_amount, status) VALUES
-- First 10 (1-5 March) with status TRUE
(1, 3, 7, 1, NULL, '2025-03-01 09:00:00', '2025-03-01 09:30:00', 'جلسة علاج طبيعي هامة', 10.0, true),
(1, 5, 2, 1, NULL, '2025-03-01 10:00:00', '2025-03-01 10:45:00', 'آلام في المفاصل والركبة', 10.0, true),
(1, 9, 4, 1, NULL, '2025-03-02 14:00:00', '2025-03-02 14:30:00', 'جلسة تقوية العضلات', 10.0, true),
(1, 15, 8, 1, NULL, '2025-03-02 16:00:00', '2025-03-02 16:45:00', 'تأهيل بعد الجراحة', 20.0, true),
(1, 18, 6, 1, NULL, '2025-03-03 11:00:00', '2025-03-03 11:40:00', 'إصابة في العمود الفقري', 50.0, true),
(1, 2, 5, 2, 1, '2025-03-03 08:30:00', '2025-03-03 09:15:00', 'علاج طبيعي لآلام الظهر', 30.0, true),
(1, 7, 3, 2, 2, '2025-03-04 12:00:00', '2025-03-04 12:30:00', 'جلسة تدليك عضلي', 40.0, true),
(1, 12, 10, 2, 1, '2025-03-04 15:00:00', '2025-03-04 15:45:00', 'تقوية الركبة بعد الإصابة', 35.0, true),
(1, 14, 9, 2, 2, '2025-03-05 18:00:00', '2025-03-05 18:45:00', 'جلسة رياضية علاجية', 50.0, true),
(1, 19, 11, 2, 1, '2025-03-05 17:00:00', '2025-03-05 17:30:00', 'إصابة قديمة تحتاج متابعة', 45.0, true),

-- Next 10 (6-10 March) status FALSE
(1, 1, 4, 1, NULL, '2025-03-06 10:00:00', '2025-03-06 10:30:00', 'جلسة استرخاء عضلي', 10.0, false),
(1, 6, 3, 2, 2, '2025-03-06 11:30:00', '2025-03-06 12:00:00', 'علاج طبيعي للكتف', 40.0, false),
(1, 8, 7, 1, NULL, '2025-03-07 09:00:00', '2025-03-07 09:45:00', 'تأهيل بعد إصابة رياضية', 10.0, false),
(1, 10, 2, 1, NULL, '2025-03-07 14:30:00', '2025-03-07 15:00:00', 'جلسة تدليك علاجي', 20.0, false),
(1, 16, 5, 2, 1, '2025-03-08 13:00:00', '2025-03-08 13:45:00', 'تمزق أربطة الركبة', 30.0, false),
(1, 17, 9, 1, NULL, '2025-03-08 16:30:00', '2025-03-08 17:00:00', 'تقوية عضلات الظهر', 10.0, false),
(1, 20, 6, 2, 2, '2025-03-09 08:00:00', '2025-03-09 08:45:00', 'تأهيل مفصل الكاحل', 45.0, false),
(1, 13, 10, 1, NULL, '2025-03-09 10:30:00', '2025-03-09 11:00:00', 'إعادة تأهيل بعد حادث', 50.0, false),
(1, 11, 11, 2, 1, '2025-03-10 15:30:00', '2025-03-10 16:15:00', 'جلسة تدليك لآلام الرقبة', 35.0, false),
(1, 3, 8, 2, 2, '2025-03-10 17:30:00', '2025-03-10 18:00:00', 'علاج طبيعي للركبة', 50.0, false),

-- Next 30 (11-25 March) status FALSE, incremental dates
(1, 5, 2, 1, NULL, '2025-03-11 08:00:00', '2025-03-11 08:30:00', 'إصابة مزمنة في المفصل', 10.0, false),
(1, 15, 7, 2, 2, '2025-03-12 12:30:00', '2025-03-12 13:00:00', 'جلسة كهربائية للعضلات', 50.0, false),
(1, 9, 4, 1, NULL, '2025-03-13 10:00:00', '2025-03-13 10:30:00', 'علاج طبيعي لآلام الرقبة', 10.0, false),
(1, 8, 6, 2, 1, '2025-03-14 18:00:00', '2025-03-14 18:30:00', 'إصابة في الكاحل تحتاج متابعة', 30.0, false),
(1, 19, 5, 1, NULL, '2025-03-15 16:00:00', '2025-03-15 16:45:00', 'جلسة تقوية عضلية', 20.0, false),
(1, 4, 10, 2, 2, '2025-03-16 14:00:00', '2025-03-16 14:45:00', 'جلسة علاج العمود الفقري', 40.0, false),
(1, 7, 1, 1, NULL, '2025-03-17 10:30:00', '2025-03-17 11:15:00', 'جلسة استشفاء عضلي', 20.0, false),
(1, 10, 3, 2, 1, '2025-03-18 08:30:00', '2025-03-18 09:00:00', 'جلسة متابعة بعد الجراحة', 30.0, false),
(1, 6, 2, 1, NULL, '2025-03-19 11:00:00', '2025-03-19 11:30:00', 'علاج طبيعي للكوع', 10.0, false),
(1, 12, 5, 2, 2, '2025-03-20 13:00:00', '2025-03-20 13:30:00', 'جلسة تدليك لتخفيف التوتر', 40.0, false);



INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (1, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (2, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (3, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (4, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (5, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (6, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (7, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (8, 1);
INSERT INTO insurance_customer (insurance_id, customer_id) VALUES (9, 1);