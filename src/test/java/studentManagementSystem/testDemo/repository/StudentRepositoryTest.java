package studentManagementSystem.testDemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import studentManagementSystem.testDemo.data.Student;

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
}
