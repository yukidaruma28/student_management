package studentManagementSystem.testDemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
  private StudentDetail studentDetail = new StudentDetail();
  private Student student;
  private List<StudentCourse> studentCourses;
  private List<StudentCourse> studentCourseList;

  // メソッド全体でやりたいことは、先にまとめておく
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);

    // studentCoursesという大箱に対して、courseという箱を用意して、その箱に属性をsetする

//    builderでの実装コード
//    StudentCourse course = StudentCourse.builder()
//        .studentsCoursesId("1")
//        .studentId("1")
//        .courseName("Javaコース")
//        .startDate(Timestamp.valueOf("2025-08-01 00:00:00"))
//        .endDate(Timestamp.valueOf("2025-08-01 00:00:00"))
//        .build();

    // コンストラクタでの実装
    // 参考URL：https://efficientify.secret.jp/development/programming/%E3%80%90java%E5%85%A5%E9%96%80%E3%80%91java%E3%81%A7list%E3%82%92%E5%88%9D%E6%9C%9F%E5%8C%96%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95%EF%BC%9A%E5%88%9D%E5%BF%83%E8%80%85%E3%81%AB%E5%84%AA%E3%81%97/?utm_source=chatgpt.com
    student = new Student(
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

    studentCourses = List.of(
        new StudentCourse(
        "1",
        "1",
        "Javaコース",
        Timestamp.valueOf("2025-08-01 00:00:00"),
        Timestamp.valueOf("2026-08-01 00:00:00")
      )
    );
  }

  // 40_課題 すべてのServiceのテスト実装する
  // privateがついていたら、sutで呼び出せない。なので、Serviceのprivateを消して、voidから始める
  // initはチャレンジ課題

  @Test
  void 受講生詳細の検索_リポジトリとコンバーターが適切に呼び出せていること() {
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

  @Test
  void 受講生詳細検索機能_リポジトリが適切に呼び出せていること() {

//    // 検証するServiceのコード
//    public StudentDetail searchStudent(String studentId) {
//    Student student = repository.searchStudentOne(studentId);
//    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getStudentId());
//    return new StudentDetail(student, studentCourse);
//  }
    // todo studentにおけるリポジトリが呼び出せている
    // todo studentCourseにおけるリポジトリが呼び出せている
    // todo StudentDetailの中身におけるstudentとstudentCourseが、上記の2つと一致している

    // 準備
    when(repository.searchStudentOne("1")).thenReturn(student);
    when(repository.searchStudentCourse("1")).thenReturn(studentCourses);

    // 実行
    Student expected = student;
    List<StudentCourse> actual = sut.searchStudent("1").getStudentCourseList();

    // 検証
    verify(repository, times(1)).searchStudentOne("1");
    verify(repository, times(1)).searchStudentCourse("1");
    assertEquals(expected.getStudentId(), actual.getFirst().getStudentId());
  }

  @Test
  void 受講生と受講生コース情報の登録_適切に定義を行ってリポジトリが呼び出せていること() {
//    // 検証するServiceのコード
//    public StudentDetail registerStudent(StudentDetail studentDetail) {
//    Student student = studentDetail.getStudent();
//
//    repository.registerStudent(student);
//    studentDetail.getStudentCourseList().forEach(studentsCourses -> {
//      initStudentsCourse(studentsCourses, student);
//      repository.registerStudentCourse(studentsCourses);
//    });
//    return studentDetail;
//  }

    // todo studentとstudentCourseListを定義する
    // todo studentと、studentDetailのgetStudentとの値が一致している
    // todo repositoryの引数がstudentCourseと一致している

    // 準備
    StudentDetail expected = studentDetail;

    // 実行
    StudentDetail actual = sut.registerStudent(studentDetail);

    // 検証
    verify(repository, times(1)).registerStudent(student);
    assertEquals(expected.getStudent().getStudentId(), actual.getStudent().getStudentId());

  }

  @Test
  void 受講生と受講生コース情報の更新_適切に定義を行いリポジトリが呼び出せていること() {
//    // 検証するServiceのコード
//    public void updateStudent(StudentDetail studentDetail) {
//      repository.updateStudent(studentDetail.getStudent());
//      studentDetail.getStudentCourseList()
//          .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
//    }
    // todo updateStudentを定義する
    // todo studentDetail.getStudentCourseListを使えるようにする

    // 準備
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    // 実行
    sut.updateStudent(studentDetail);

    // 検証
    verify(repository, times(1)).updateStudent(student);

  }
}