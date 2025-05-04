package studentManagementSystem.testDemo.Controller;

import java.util.Arrays;
import org.apache.ibatis.annotations.Insert;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.repository.StudentRepository;
import studentManagementSystem.testDemo.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うREST APIとして受け付けるControllerです
 */
@RestController
public class StudentController {

  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;

  }

  /**
   * 受講生検索一覧機能です
   * 全件検索を実行します
   * 条件指定検索は実行しません
   * @return 受講生一覧(全件)
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索機能です
   * IDに基づく任意の受講生の情報を取得します
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  @GetMapping("/student/{studentId}")
  public StudentDetail getStudent(@PathVariable String studentId) {
    return service.searchStudent(studentId);
  }


  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }


  @PostMapping ("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }


  @PostMapping ("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
