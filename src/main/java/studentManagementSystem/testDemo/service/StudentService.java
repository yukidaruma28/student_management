package studentManagementSystem.testDemo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
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
   * 受講生検索一覧機能です 全件検索を実行します
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
  List<Student> stundentList = repository.searchStudent();
  List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();

    return converter.convertStudentDetails(stundentList, studentsCoursesList);
  }

  /**
   * 受講生検索機能です
   *  IDに基づく受講生の情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudentOne(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getStudentId());
    return new StudentDetail(student,studentsCourses);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    for (StudentsCourses studentsCourses:studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getStudentId());
      studentsCourses.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
      studentsCourses.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
      repository.registerStudentsCourses(studentsCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    for (StudentsCourses studentsCourses:studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourses);
    }
  }
}
