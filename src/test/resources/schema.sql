CREATE TABLE IF NOT EXISTS student (
  studentId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(255) NOT NULL,
  furigana VARCHAR(255) NOT NULL,
  nickname VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  city_name VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(10) NOT NULL, -- ENUM → VARCHARに変更！
  remark VARCHAR(255),
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE -- TINYINT(1) → BOOLEAN に変更！
);


CREATE TABLE IF NOT EXISTS students_courses
(
  students_courses_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  studentId INT NOT NULL,
  select_courses VARCHAR(255) NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  student_course_status VARCHAR(36) NOT NULL, -- 旧カラム（後で削除予定）
  FOREIGN KEY (studentId) REFERENCES student(studentId)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS course_master
(
  course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  course_name VARCHAR(255) NOT NULL UNIQUE,
  description VARCHAR(500),
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS application_status (
  application_status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  students_courses_id INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  FOREIGN KEY (students_courses_id) REFERENCES students_courses(students_courses_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
