package studentManagementSystem.testDemo.Controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;

class StudentConverterTest {

  private List<Student> studentList;
  private List<StudentCourse> studentCourseList;
  private StudentDetail studentDetail;

  @BeforeEach
  void before() {
    studentList = new ArrayList<>();
    studentCourseList = new ArrayList<>();

    studentList.add(new Student(
        "1",
        "山田太郎",
        "やまだたろう",
        "タロー",
        "taro@example",
        "鹿児島",
        20,
        "男性",
        "未経験転職するために、東京へ上京予定。",
        false
    ));

    studentCourseList.add(new StudentCourse(
        "1",
        "1",
        "Javaコース",
        Timestamp.valueOf("2025-08-01 00:00:00"),
        Timestamp.valueOf("2026-08-01 00:00:00"),
        "仮申込"
    ));
  }


  @Test
  void 二つのリストに値を渡した後リスト形式になっているか () {
  // todo studentListとstudentCourseListにそれぞれ値を渡す
  // todo StudentDetailのListになっているかどうかチェック

  // 準備

  // 実行
  List<Student> expected = studentList;
  List<StudentCourse> actual = studentCourseList;

  // 検証

  assertEquals(expected.get(0).getStudentId(), actual.get(0).getStudentId());


  }
}
