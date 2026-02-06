package studentManagementSystem.testDemo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.CourseMaster;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.data.ApplicationStatus;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.domain.StudentSearchCondition;
import studentManagementSystem.testDemo.repository.ApplicationStatusRepository;
import studentManagementSystem.testDemo.repository.StudentRepository;

/**
 * 受講生情報を扱うサービスです
 * 受講生の検索や登録、更新の処理を行います
 * 
 */
@Service
public class StudentService {
  private StudentRepository repository;
  private ApplicationStatusRepository applicationStatusRepository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,
                        ApplicationStatusRepository applicationStatusRepository,
                        StudentConverter converter) {
    this.repository = repository;
    this.applicationStatusRepository = applicationStatusRepository;
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

    // 各コースに申込状況を設定
    studentCourse.forEach(course -> {
      List<ApplicationStatus> statusList = applicationStatusRepository
          .findByStudentsCoursesId(course.getStudentsCoursesId());
      if (!statusList.isEmpty()) {
        course.setApplicationStatus(statusList.get(0));
      }
    });

    return new StudentDetail(student, studentCourse);
  }

  /**
   * 検索条件に基づいて受講生を検索します
   *
   * @param condition 検索条件
   * @return 検索条件に一致する受講生一覧
   */
  public List<Student> searchStudentAll(StudentSearchCondition condition) {
    return repository.searchStudentAll(condition);
  }

  /**
   * 検索条件に基づいて受講生詳細を検索します
   * 検索条件が空の場合は全件を返します
   *
   * @param condition 検索条件
   * @return 検索条件に一致する受講生詳細一覧
   */
  public List<StudentDetail> searchStudentWithCondition(StudentSearchCondition condition) {
    // 検索条件が空の場合は全件検索
    if (isEmptyCondition(condition)) {
      return searchStudentList();
    }

    // 条件付き検索を実行
    List<Student> studentList = repository.searchStudentAll(condition);

    // 各受講生に紐づくコース情報を取得してStudentDetailに変換
    return studentList.stream()
        .map(student -> {
          List<StudentCourse> courseList = repository.searchStudentCourse(student.getStudentId());

          // 各コースに申込状況を設定
          courseList.forEach(course -> {
            List<ApplicationStatus> statusList = applicationStatusRepository
                .findByStudentsCoursesId(course.getStudentsCoursesId());
            if (!statusList.isEmpty()) {
              course.setApplicationStatus(statusList.get(0));
            }
          });

          return new StudentDetail(student, courseList);
        })
        .toList();
  }

  /**
   * 検索条件が空かどうかを判定します
   *
   * @param condition 検索条件
   * @return 空の場合true
   */
  private boolean isEmptyCondition(StudentSearchCondition condition) {
    if (condition == null) {
      return true;
    }

    Student student = condition.getStudent();
    StudentCourse course = condition.getStudentCourse();

    // Studentの検索条件チェック
    boolean isStudentEmpty = student == null ||
        (isEmpty(student.getFullName()) &&
         isEmpty(student.getFurigana()) &&
         isEmpty(student.getEmail()));

    // StudentCourseの検索条件チェック
    boolean isCourseEmpty = course == null ||
        (isEmpty(course.getSelectCourses()) &&
         isEmpty(course.getStudentCourseStatus()));

    return isStudentEmpty && isCourseEmpty;
  }

  /**
   * 文字列が空かどうかを判定します
   *
   * @param str 文字列
   * @return 空の場合true
   */
  private boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();
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

      // 申込状況を登録
      if (studentsCourses.getStudentCourseStatus() != null) {
        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStudentsCoursesId(studentsCourses.getStudentsCoursesId());
        applicationStatus.setStatus(studentsCourses.getStudentCourseStatus());
        applicationStatusRepository.insert(applicationStatus);
        studentsCourses.setApplicationStatus(applicationStatus);
      }
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
        .forEach(studentCourse -> {
          repository.updateStudentCourse(studentCourse);

          // 申込状況を更新
          if (studentCourse.getApplicationStatus() != null &&
              studentCourse.getApplicationStatus().getApplicationStatusId() != null) {
            ApplicationStatus applicationStatus = studentCourse.getApplicationStatus();
            applicationStatus.setStatus(studentCourse.getStudentCourseStatus());
            applicationStatusRepository.update(applicationStatus);
          }
        });
  }

  /**
   * 有効なコース一覧を取得します
   *
   * @return コース一覧
   */
  public List<CourseMaster> getActiveCourses() {
    return repository.getActiveCourses();
  }
}
