INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Scott','Normore','snorm@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Bill','Nye','bnye@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Bill','Mays','bmays@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('John','Doe','jdoe@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Mary','Sue','msue@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Gary','Stu','gstu@gmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',false,null,null);
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('Benjamin','Pierce','hawkeye@live.ca','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',true,'ABCD-1234-1234','Surgeon');
INSERT INTO Users (first_name,last_name,email,password,is_doctor, medical_license_number,specialization) VALUES ('John','Mclntyre','trapper@hotmail.com','$2a$10$FNxTX2FZcO4tzU6EQVcERun1hQUG5fsrSayamoa8ujcLJ16MOrdle',true,'ABCD-4321-4321','Surgeon');

INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (7,1);
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (7,2);
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (7,3);
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (8,4);
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (8,5);
INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (8,6);

INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (2, '250.90','1.9',2300,72, '1972-02-02');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (2, '240.54','1.9',6100,73, '1972-03-30');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (2, '230.21','1.9',12400,71, '1972-04-27');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (3, '200.11','1.7',3201,95, '1992-04-27');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (3, '180.29','1.7',12123,97, '1992-05-13');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (4, '160.45','1.5',14341,104, '2001-04-23');
INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (6, '199.21','2.1',6500,56, '2018-03-27');

INSERT INTO Medicine_Reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (2, 'Insulin', '10 ml injection','daily', '1972-02-02', '1972-04-27');
INSERT INTO Medicine_Reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (6, 'Insulin', '10 ml injection','daily', '2018-03-27', '2018-04-27');

INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (2,'Try to improve your diet/eating practices','1972-02-02');
INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (3,'Try to improve your diet/eating practices','1992-04-27');
INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (4,'Heart rate to high; watch your blood sugar level!','2001-04-23');
INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (6,'Try to improve your diet/eating practices','2018-03-27');