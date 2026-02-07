-- =============================================================================
-- Migration Script v2.0
-- Purpose: Align database schema with specification document
--
-- Changes:
-- 1. Rename student columns: name → full_name, area → city_name
-- 2. Rename students_courses columns: course_name → select_courses, course_start_date → start_date
-- 3. Create new application_status table
-- 4. Migrate data from student_course_status to application_status
--
-- CRITICAL: BACKUP DATABASE BEFORE RUNNING THIS SCRIPT
-- Backup command: mysqldump -u root -p students_management > backup_before_migration_v2.sql
-- =============================================================================

-- Step 1: Backup verification
SELECT 'CRITICAL: Ensure you have backed up the database before proceeding!' as WARNING;

-- Step 2: Create new application_status table
CREATE TABLE IF NOT EXISTS application_status (
  application_status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  students_courses_id INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  FOREIGN KEY (students_courses_id) REFERENCES students_courses(students_courses_id)
);

-- Step 3: Migrate student_course_status data to application_status table
INSERT INTO application_status (students_courses_id, status)
SELECT students_courses_id, student_course_status
FROM students_courses
WHERE student_course_status IS NOT NULL;

-- Step 4: Verify data migration
SELECT
  'Data Migration Check' as step,
  (SELECT COUNT(*) FROM students_courses WHERE student_course_status IS NOT NULL) as old_count,
  (SELECT COUNT(*) FROM application_status) as new_count,
  CASE
    WHEN (SELECT COUNT(*) FROM students_courses WHERE student_course_status IS NOT NULL) = (SELECT COUNT(*) FROM application_status)
    THEN 'SUCCESS: Data migrated correctly'
    ELSE 'ERROR: Data migration failed'
  END as status;

-- Step 5: Rename columns in student table
ALTER TABLE student CHANGE COLUMN name full_name VARCHAR(255) NOT NULL;
ALTER TABLE student CHANGE COLUMN area city_name VARCHAR(255) NOT NULL;

-- Step 6: Rename columns in students_courses table
ALTER TABLE students_courses CHANGE COLUMN course_name select_courses VARCHAR(255) NOT NULL;
ALTER TABLE students_courses CHANGE COLUMN course_start_date start_date TIMESTAMP NOT NULL;

-- Step 7: Verify schema changes
SHOW COLUMNS FROM student;
SHOW COLUMNS FROM students_courses;
SHOW COLUMNS FROM application_status;

-- Step 8: Data integrity check
SELECT
  'Integrity Check' as step,
  sc.students_courses_id,
  sc.student_course_status as old_status,
  a.status as new_status
FROM students_courses sc
LEFT JOIN application_status a ON sc.students_courses_id = a.students_courses_id
WHERE sc.student_course_status != a.status
LIMIT 10;

-- =============================================================================
-- IMPORTANT: DO NOT RUN STEP 9 UNTIL ALL TESTS PASS
-- =============================================================================

-- Step 9: Drop old column (ONLY after confirming all tests pass)
-- ALTER TABLE students_courses DROP COLUMN student_course_status;

-- =============================================================================
-- Rollback Script (if needed)
-- =============================================================================
-- ALTER TABLE student CHANGE COLUMN full_name name VARCHAR(255) NOT NULL;
-- ALTER TABLE student CHANGE COLUMN city_name area VARCHAR(255) NOT NULL;
-- ALTER TABLE students_courses CHANGE COLUMN select_courses course_name VARCHAR(255) NOT NULL;
-- ALTER TABLE students_courses CHANGE COLUMN start_date course_start_date TIMESTAMP NOT NULL;
-- DROP TABLE application_status;
-- =============================================================================
