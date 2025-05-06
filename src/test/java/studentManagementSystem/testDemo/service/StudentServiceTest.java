package studentManagementSystem.testDemo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.repository.StudentRepository;


@ExtendWith(MockitoExtension.class) //mock化する
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  // メソッド全体でやりたいことは、先にまとめておく
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);

  }

  // 40_課題 すべてのServiceのテスト実装する
  // privateがついていたら、sutで呼び出せない。なので、Serviceのprivateを消して、voidから始める
  // initはチャレンジ課題


  @Test
  void 受講生詳細の検索_リポジトリーとコンバーターが適切に呼び出せていること() {
    // 事前準備
    // ①List<StudentDetail> expected = new ArrayList<>(); // actualがうまくいけば、expectedに入る

    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent()).thenReturn(studentList); // 空のstudentListを返す
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList); // 空のstudentCourseListを返す

    // 実行
    List<StudentDetail> actual = sut.searchStudentList(); // テストを検証する対象

    // 検証
    // こうなっているだろうという値を先にexpectedで渡して、実行結果をactualという形で渡す
    // assertEqualsなので、イコールになっていればOK　
    // ①Assertions.assertEquals(expected, actual);

    verify(repository, times(1)).searchStudent();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    // 後処理：DBに変更を加えてしまうときは、ここで直す
  }
}