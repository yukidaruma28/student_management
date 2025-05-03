package studentManagementSystem.testDemo.repository;

// これはSQLを実行するためのインターフェースという考え方
// Webの世界から検索や登録をするインターフェース

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  // 全件取得
  @Select("SELECT * FROM student")
  List<Student> searchStudent();

  // 全件取得
  @Select("SELECT * FROM student WHERE studentId = #{studentId}")
  Student searchStudentOne(String studentId);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(String studentId);


  // ID全件取得
  @Select("SELECT id FROM student")
  List<Integer> searchStudentId();

  // 指定したIDの情報を一括取得
  @Select("SELECT * FROM student WHERE id = #{id}")
  Student findStudentId(int id); // ここのStudentはStudentの中身を全部返す意味

  @Select("SELECT * FROM student WHERE id = #{id}")
  Student searchStudentById(int id); // ここのStudentはStudentの中身を全部返す意味


  // 名前全件取得
  @Select("SELECT name FROM student")
  List<String> searchStudentName();

  // ふりがな全件取得
  @Select("SELECT furigana FROM student")
  List<String> searchStudentFurigana();

  // ニックネーム全件取得
  @Select("SELECT nickname FROM student")
  List<String> searchStudentNickname();

  // メールアドレス全件取得
  @Select("SELECT email FROM student")
  List<String> searchStudentEmail();

  // 居住地全件取得
  @Select("SELECT area FROM student")
  List<String> searchStudentArea();

  // 年齢全件取得
  @Select("SELECT age FROM student")
  List<String> searchStudentAge();

  // 性別全件取得
  @Select("SELECT gender FROM student")
  List<String> searchStudentGender();

  // INSERT INTO テーブル名 (列名1, 列名2, 列名3) VALUES (値1, 値2, 値3)
//  @Insert("INSERT INTO test(name) VALUES(#{student.name})")
//  void registerStudent(StudentDetail studentDetail);

// 自分のコード
//  @Insert("""
//  INSERT INTO student
//    (name, furigana, nickname, email, area, age, gender, remark, isDeleted)
//  VALUES
//    (#{student.name}, #{student.furigana}, #{student.nickname}, #{student.email},
//     #{student.area}, #{student.age}, #{student.gender}, #{student.remark}, #{student.isDeleted})
//""")
//    void registerStudent(StudentDetail studentDetail);

  // 29_のコード
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

// 課題① studentのRead処理を実装する
// 課題② students_coursesの全件取得を実装する