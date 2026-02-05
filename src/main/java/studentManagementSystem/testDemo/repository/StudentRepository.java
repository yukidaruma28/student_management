package studentManagementSystem.testDemo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import studentManagementSystem.testDemo.data.CourseMaster;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentSearchCondition;

/**
 * 受講生情報テーブルと受講生コース情報テーブルと紐づくRepositoryです
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います
   *
   * @return 受講生一覧(一覧)
   */
  List<Student> searchStudent();

  /**
   * IDに紐づく受講生の検索を行います
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  Student searchStudentOne(String studentId);

  /**
   * 受講生コース情報の全件検索を行います
   *
   * @return 受講生コース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生コース情報に紐づく受講生コース情報の検索を行います
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 検索条件に基づいて受講生を検索します
   *
   * @param condition 検索条件
   * @return 検索条件に一致する受講生一覧
   */
  List<Student> searchStudentAll(StudentSearchCondition condition);


  /**
   * 受講生を新規登録します
   * IDについては自動採番を行います
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します
   * IDについては自動採番を行います
   *
   * @param studentCourse 受講生コース情報
   */
    void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報を更新します
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 有効なコース一覧を取得します
   *
   * @return コースマスター一覧
   */
  List<CourseMaster> getActiveCourses();
}
