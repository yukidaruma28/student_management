package studentManagementSystem.testDemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(15);

  }

  @Test
  void 受講生コースの全件検索が行えること() {
    Student student = new Student(
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
    );

    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(20);

  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student(
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
    );

    sut.registerStudent(student);
    List<Student> actual = sut.searchStudent();

    assertThat(actual.size()).isEqualTo(16);

  }

  @Test
  void 受講生の更新が行えること() {
    Student expected = new Student(
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
    );

    Student actual = sut.searchStudent().stream()
        .filter(s -> s.getStudentId().equals("1"))
        .findFirst()
        .orElse(null);

    sut.updateStudent(expected);

    assertNotEquals(expected, actual);
    assertNotEquals(expected.getName(), actual.getName());

  }

  @Test
  void 受講コース情報の更新が行えること() {
    List<StudentCourse> expected = List.of(
        new StudentCourse(
            "1",
            "1",
            "iPSコース",
            Timestamp.valueOf("2022-02-02 00:00:00"),
            Timestamp.valueOf("2023-02-02 00:00:00")
        )
    );

    sut.searchStudentCourseList();

    StudentCourse actual = sut.searchStudentCourseList().stream()
        .filter(s -> s.getStudentId().equals("1"))
        .findFirst()
        .orElse(null);

    assertNotEquals(expected, actual);

  }
}
