package studentManagementSystem.testDemo.repository;

// これはSQLを実行するためのインターフェースという考え方
// Webの世界から検索や登録をするインターフェース

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  // 全件取得
  @Select("SELECT * FROM student")
  List<Student> searchStudent();

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


  // students_coursesの全件取得
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();
}

// 課題① studentのRead処理を実装する
// 課題② students_coursesの全件取得を実装する