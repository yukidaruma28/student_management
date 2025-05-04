package studentManagementSystem.testDemo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;

/**
 * 受講生情報テーブルと受講生コース情報テーブルと紐づくRepositoryです
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います
   *
   * @return 受講生一覧(一覧)
   */
  @Select("SELECT * FROM student")
  List<Student> searchStudent();

  /**
   * IDに紐づく受講生の検索を行います
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  @Select("SELECT * FROM student WHERE studentId = #{studentId}")
  Student searchStudentOne(String studentId);

  /**
   * 受講生コース情報の全件検索を行います
   *
   * @return 受講生コース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  /**
   * 受講生コース情報に紐づく受講生コース情報の検索を行います
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(String studentId);


  @Insert("INSERT INTO student(name, furigana, nickname, email, area, age, gender, remark, is_deleted) "
      + "VALUES(#{name}, #{furigana}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")

  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (studentId, course_name, course_start_date, end_date) "
      + "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "studentsCoursesId")

  void registerStudentsCourses(StudentsCourses studentsCourses);

  @Update("UPDATE student SET name = #{name}, furigana = #{furigana}, nickname = #{nickname}, "
      + "email = #{email}, area = #{area}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} "
      + "WHERE studentId = #{studentId}")

  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE students_courses_id = #{studentsCoursesId}")

  void updateStudentsCourses(StudentsCourses studentsCourses);
}
