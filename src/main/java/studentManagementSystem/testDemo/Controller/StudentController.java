package studentManagementSystem.testDemo.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.exception.TestException;
import studentManagementSystem.testDemo.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うREST APIとして受け付けるControllerです
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;

  }

  /**
   * 受講生詳細検索一覧機能です
   * 全件検索を実行します
   * 条件指定検索は実行しません
   * @return 受講生詳細一覧(全件)
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() throws TestException {
    throw new TestException("TestException エラーが発生しました");
//    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索機能です
   * IDに基づく任意の受講生の情報を取得します
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  @GetMapping("/student/{studentId}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String studentId) {
    return service.searchStudent(studentId);
  }


  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourseList(Arrays.asList(new StudentCourse()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  /**
   * 受講生詳細の登録を行います
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping ("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います
   * isDeleted(論理削除)の更新もここで行います
   * @param studentDetail
   * @return
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  // 課題36 ExceptionHandlerをController以外でも動くように、classで例外処理ができる
  // Controllerとかで、パッケージhandlerを作り、 ExceptionHandlingをする、専用のclassを作って、例外処理を実装する
  // 例外を発生させるように専用のメソッドを作る -> テキトーなclassを作る -> 例外処理が継続してできるようにする
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
