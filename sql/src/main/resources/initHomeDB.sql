CREATE TABLE groups(
  group_id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  group_name VARCHAR (20)
);


CREATE TABLE students(
  student_id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  student_name VARCHAR (20),
  group_id int, FOREIGN KEY (group_id) REFERENCES groups(group_id)
);

CREATE TABLE subjects(
  subject_id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  subject_title VARCHAR (20),
  subject_desc VARCHAR(50)
);

CREATE TABLE teachers(
  teacher_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  teacher_name VARCHAR(20),
  teacher_exp DOUBLE,
  subject_id int, FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

CREATE TABLE learning(
  group_id int,
  subject_id int,
  FOREIGN KEY (group_id) REFERENCES groups(group_id),
  FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

