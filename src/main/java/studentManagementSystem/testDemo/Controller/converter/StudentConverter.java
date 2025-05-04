package studentManagementSystem.testDemo.Controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;

/**
 * Serviceから取得したオブジェクトをControllerにとって必要な形に変換するConverterです
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる
   *
   * @param studentList 受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>(); // studentDetailsというList箱を作った
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail(); // List箱の中で使うstudentDetailを作った
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(StudentCourse -> student.getStudentId().equals(StudentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}
