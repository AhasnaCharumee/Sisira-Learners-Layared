DROP DATABASE IF EXISTS DrivingSchoolDB;
CREATE DATABASE DrivingSchoolDB;
USE DrivingSchoolDB;

CREATE TABLE User (
                      user_id VARCHAR(10) PRIMARY KEY,
                      user_name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL
);

CREATE TABLE Course (
                        course_id VARCHAR(10) PRIMARY KEY,
                        status VARCHAR(20),
                        name VARCHAR(100) NOT NULL,
                        duration INT NOT NULL,
                        price DECIMAL(10,2) NOT NULL
);

CREATE TABLE Student (
                         student_id VARCHAR(10) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100),
                         nic VARCHAR(20),
                         contact VARCHAR(15),
                         pay_balance DECIMAL(10,2)
);

CREATE TABLE StudentRegistration (
                                     registration_id VARCHAR(10) PRIMARY KEY,
                                     student_id VARCHAR(10),
                                     course_id VARCHAR(10),
                                     registration_date DATE NOT NULL,
                                     status VARCHAR(20),
                                     FOREIGN KEY (student_id) REFERENCES Student(student_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (course_id) REFERENCES Course(course_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment (
                         payment_id VARCHAR(10) PRIMARY KEY,
                         student_id VARCHAR(10),
                         note VARCHAR(100),
                         amount DECIMAL(10,2) NOT NULL,
                         payment_date DATE NOT NULL,
                         FOREIGN KEY (student_id) REFERENCES Student(student_id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Exam (
                      exam_id VARCHAR(10) PRIMARY KEY,
                      exam_name VARCHAR(100),
                      student_id VARCHAR(10),
                      exam_date DATE NOT NULL,
                      result VARCHAR(20),
                      FOREIGN KEY (student_id) REFERENCES Student(student_id)
                          ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Instructor (
                            instructor_id VARCHAR(10) PRIMARY KEY,
                            name VARCHAR(50),
                            email VARCHAR(50),
                            contact VARCHAR(20),
                            vehicle_class VARCHAR(10)
);

CREATE TABLE Vehicle (
                         vehicle_id VARCHAR(10) PRIMARY KEY,
                         vehicle_name VARCHAR(20),
                         vehicle_number VARCHAR(20) UNIQUE NOT NULL,
                         engine_number VARCHAR(50) NOT NULL,
                         vehicle_class VARCHAR(20),
                         status VARCHAR(100)
);

CREATE TABLE Session (
                          session_id VARCHAR(10) PRIMARY KEY,
                          instructor_id VARCHAR(10),
                          vehicle_id VARCHAR(10),
                          day VARCHAR(20),
                          start_time VARCHAR(20),
                          end_time VARCHAR(20),
                          FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id)
                              ON DELETE CASCADE ON UPDATE CASCADE,
                          FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
                              ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Booking (
                         booking_id VARCHAR(10) PRIMARY KEY,
                         student_id VARCHAR(10),
                         session_id VARCHAR(10),
                         booking_date DATE NOT NULL,
                         practise_session VARCHAR(100),
                         FOREIGN KEY (student_id) REFERENCES Student(student_id)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (session_id) REFERENCES Session(session_id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO User (user_id, user_name, email, password, role) VALUES
                                                            ('U001', 'admin', 'admin@example.com', '1234', 'Admin'),
                                                            ('U002', 'Jane Smith', 'jane.smith@example.com', 'password456', 'User'),
                                                            ('U003', 'Alice Johnson', 'alice.j@example.com', 'password789', 'User'),
                                                            ('U004', 'Bob Brown', 'bob.brown@example.com', 'password101', 'User'),
                                                            ('U005', 'Carol White', 'carol.white@example.com', 'password112', 'User');

INSERT INTO Course (course_id, status, name, duration, price) VALUES
                                                                  ('C001', 'Active', 'Beginner Driving', 30, 500.00),
                                                                  ('C002', 'Active', 'Advanced Driving', 20, 700.00),
                                                                  ('C003', 'Inactive', 'Defensive Driving', 25, 600.00),
                                                                  ('C004', 'Active', 'Motorcycle Training', 15, 400.00),
                                                                  ('C005', 'Active', 'Commercial Vehicle', 40, 1000.00);

INSERT INTO Student (student_id, name, email, nic, contact, pay_balance) VALUES
                                                                ('S001', 'Alice Johnson', 'alice.j@example.com', '123456789V', '0771234567',200.00),
                                                                ('S002', 'Bob Brown', 'bob.brown@example.com', '987654321V', '0772345678',500.00),
                                                                ('S003', 'Charlie Davis', 'charlie.d@example.com', '456789123V', '0773456789',150.00),
                                                                ('S004', 'Diana Evans', 'diana.e@example.com', '321654987V', '0774567890',500.00),
                                                                ('S005', 'Ethan Green', 'ethan.g@example.com', '654123987V', '0775678901',150.00);

INSERT INTO StudentRegistration (registration_id, student_id, course_id, registration_date, status) VALUES
                                                                                                             ('SR001', 'S001', 'C001', '2023-10-01','Active'),
                                                                                                             ('SR002', 'S002', 'C002', '2023-10-02','Active'),
                                                                                                             ('SR003', 'S003', 'C003', '2023-10-03','Inactive'),
                                                                                                             ('SR004', 'S004', 'C004', '2023-10-04','Active'),
                                                                                                             ('SR005', 'S005', 'C005', '2023-10-05','Active');

INSERT INTO Payment (payment_id, student_id, note, amount, payment_date) VALUES
                                                                                              ('P001', 'S001', 'Initial Payment', 300.00, '2023-10-01'),
                                                                                              ('P002', 'S002', 'Course Fee', 400.00, '2023-10-02'),
                                                                                              ('P003', 'S003', 'Partial Payment', 200.00, '2023-10-03'),
                                                                                              ('P004', 'S004', 'Full Payment', 400.00, '2023-10-04'),
                                                                                              ('P005', 'S005', 'Initial Payment', 500.00, '2023-10-05');

INSERT INTO Exam (exam_id, exam_name, student_id, exam_date, result) VALUES
                                                                         ('E001', 'Beginner Driving Test', 'S001', '2023-11-01', 'Pass'),
                                                                         ('E002', 'Advanced Driving Test', 'S002', '2023-11-02', 'Fail'),
                                                                         ('E003', 'Defensive Driving Test', 'S003', '2023-11-03', 'Pass'),
                                                                         ('E004', 'Motorcycle Test', 'S004', '2023-11-04', 'Pass'),
                                                                         ('E005', 'Commercial Vehicle Test', 'S005', '2023-11-05', 'Fail');

INSERT INTO Instructor (instructor_id, name, email, contact, vehicle_class) VALUES
                                                                                ('I001', 'Jane Smith', 'jane.smith@example.com', '0771111111', 'B'),
                                                                                ('I002', 'Carol White', 'carol.white@example.com', '0772222222', 'A'),
                                                                                ('I003', 'David Black', 'david.b@example.com', '0773333333', 'C'),
                                                                                ('I004', 'Eva Green', 'eva.g@example.com', '0774444444', 'B'),
                                                                                ('I005', 'Frank Blue', 'frank.b@example.com', '0775555555', 'D');

INSERT INTO Vehicle (vehicle_id, vehicle_name, vehicle_number, engine_number, vehicle_class, status) VALUES
                                                                                                         ('V001', 'Toyota Corolla', 'ABC-1234', 'ENG123456', 'B', 'Available'),
                                                                                                         ('V002', 'Honda Civic', 'DEF-5678', 'ENG654321', 'B', 'In Use'),
                                                                                                         ('V003', 'Suzuki Swift', 'GHI-9101', 'ENG987654', 'A', 'Available'),
                                                                                                         ('V004', 'Toyota Hilux', 'JKL-1121', 'ENG123789', 'C', 'Under Maintenance'),
                                                                                                         ('V005', 'Mitsubishi Lancer', 'MNO-3141', 'ENG456123', 'B', 'Available');

INSERT INTO Session (session_id, instructor_id, vehicle_id, day, start_time, end_time) VALUES
                                                                                            ('SE001', 'I001', 'V001', 'Monday', '09:00', '11:00'),
                                                                                            ('SE002', 'I002', 'V003', 'Tuesday', '10:00', '12:00'),
                                                                                            ('SE003', 'I003', 'V004', 'Wednesday', '13:00', '15:00'),
                                                                                            ('SE004', 'I004', 'V002', 'Thursday', '14:00', '16:00'),
                                                                                            ('SE005', 'I005', 'V005', 'Friday', '15:00', '17:00');

INSERT INTO Booking (booking_id, student_id, session_id, booking_date, practise_session) VALUES
                                                                                             ('B001', 'S001', 'SE001', '2023-10-10', 'Beginner Session'),
                                                                                             ('B002', 'S002', 'SE002', '2023-10-11', 'Advanced Session'),
                                                                                             ('B003', 'S003', 'SE003', '2023-10-12', 'Defensive Session'),
                                                                                             ('B004', 'S004', 'SE004', '2023-10-13', 'Motorcycle Session'),
                                                                                             ('B005', 'S005', 'SE005', '2023-10-14', 'Commercial Session');