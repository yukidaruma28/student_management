package studentManagementSystem.testDemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import studentManagementSystem.testDemo.data.Gender;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentSearchCondition;

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
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(20);
  }

  @Test
  void 受講生の単一検索が行えること() {
    Student expected = new Student(
        "1",
        "田中 太郎",
        "たなかたろう",
        "タロー",
        "taro@example",
        "鹿児島",
        20,
        Gender.MALE,
        "未経験転職するために、東京へ上京予定。",
        false
    );

    Student searchConditionStudent = new Student();
    searchConditionStudent.setName("田中 太郎");

    StudentSearchCondition studentSearchCondition = new StudentSearchCondition();
    studentSearchCondition.setStudent(searchConditionStudent);

    List<Student> actual = sut.searchStudentAll(studentSearchCondition);

    assertThat(actual).hasSize(1);
    Student actualStudent = actual.get(0);

    assertThat(actualStudent.getStudentId()).isEqualTo(expected.getStudentId());
    assertThat(actualStudent.getName()).isEqualTo(expected.getName());
    assertThat(actualStudent.getFurigana()).isEqualTo(expected.getFurigana());
    assertThat(actualStudent.getNickname()).isEqualTo(expected.getNickname());
    assertThat(actualStudent.getEmail()).isEqualTo(expected.getEmail());
    assertThat(actualStudent.getArea()).isEqualTo(expected.getArea());
    assertThat(actualStudent.getAge()).isEqualTo(expected.getAge());
    assertThat(actualStudent.getGender()).isEqualTo(expected.getGender());
    assertThat(actualStudent.getRemark()).isEqualTo(expected.getRemark());
    assertThat(actualStudent.getIsDeleted()).isEqualTo(expected.getIsDeleted());
  }

  @Test
  void 受講生コース情報の単一検索が行えること() {
    StudentCourse expected = new StudentCourse(
        "1",
        "1",
        "Java基礎コース123",
        Timestamp.valueOf("2022-01-01 00:00:00"),
        Timestamp.valueOf("2023-01-01 00:00:00"),
        "仮申込"
    );

    List<StudentCourse> actual = sut.searchStudentCourse("1");

    assertThat(actual).isNotEmpty();
    StudentCourse actualCourse = actual.get(0);

    assertThat(actualCourse.getStudentsCoursesId()).isEqualTo(expected.getStudentsCoursesId());
    assertThat(actualCourse.getStudentId()).isEqualTo(expected.getStudentId());
    assertThat(actualCourse.getCourseName()).isEqualTo(expected.getCourseName());
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
        Gender.MALE,
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
        Gender.MALE,
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
            Timestamp.valueOf("2023-02-02 00:00:00"),
            "仮申込"
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
