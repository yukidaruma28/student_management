package studentManagementSystem.testDemo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.repository.StudentRepository;

/**
 * 受講生情報を扱うサービスです
 * 受講生の検索や登録、更新の処理を行います
 * 
 */
@Service
public class StudentService {
  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の検索一覧機能です 全件検索を実行します
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
  List<Student> stundentList = repository.searchStudent();
  List<StudentCourse> studentCourseList = repository.searchStudentCourseList();

    return converter.convertStudentDetails(stundentList, studentCourseList);
  }

  /**
   * 受講生詳細検索機能です
   *  IDに基づく受講生の情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生詳細情報
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudentOne(studentId);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getStudentId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 受講生詳細の登録を行います
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値や日付情報（コース開始日・終了日）を設定します
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentsCourses -> {
      initStudentsCourse(studentsCourses, student);
      repository.registerStudentCourse(studentsCourses);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する
   *
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private void initStudentsCourse(StudentCourse studentCourse, Student student) {
    studentCourse.setStudentId(student.getStudentId());
    studentCourse.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
  }

  /**
   * 受講生詳細の更新を行います
   * 受講生と受講生コース情報をそれぞれ更新します
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
