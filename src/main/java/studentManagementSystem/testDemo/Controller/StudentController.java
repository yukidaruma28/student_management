package studentManagementSystem.testDemo.Controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.domain.StudentSearchCondition;
import studentManagementSystem.testDemo.exception.ErrorMessages;
import studentManagementSystem.testDemo.exception.TestException;
import studentManagementSystem.testDemo.service.StudentService;

// 38_ドキュメントの必要性と作り方 課題
// ControllerにおけるOpenAPIをすべて実装する
// https://qiita.com/Omi354/items/e52401971354cf2b4317
// https://qiita.com/crml1206/items/e47ec484af750d301953
// https://staff.persol-xtech.co.jp/hatalabo/it_engineer/527.html

/**
 * 受講生の検索や登録、更新を行うREST APIとして受け付けるControllerです
 */
@OpenAPIDefinition(info = @Info(
    title = "受講生とコース情報の登録、検索、更新、論理削除の処理",
    description = "受講生と受講生コースの情報を登録、検索、更新、論理削除を行うControllerです。"
))
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "成功"),
    @ApiResponse(responseCode = "400", description = "リクエストエラー（バリデーションなど）"),
    @ApiResponse(responseCode = "404", description = "データが見つかりません"),
    @ApiResponse(responseCode = "500", description = "サーバーエラー")
})
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
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索機能です
   * IDに基づく任意の受講生の情報を取得します
   *
   * @param studentId 受講生ID
   * @return 受講生情報
   */
  @Operation(summary = "単一検索", description = "受講生のIDに基づく情報を取得します")
  @Parameter(
      name = "studentId",
      description = "受講生ID",
      example = "1"
  )
  @GetMapping("/students/{studentId}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String studentId) {
    return service.searchStudent(studentId);
  }

  /**
   * 検索条件に基づいて受講生を検索します
   *
   * @param condition 検索条件
   * @return 検索条件に一致する受講生一覧
   */
  @Operation(summary = "条件検索", description = "検索条件に基づいて受講生を検索します")
  @PostMapping("/students/search")
  public List<Student> searchStudentAll(@RequestBody StudentSearchCondition condition) {
    return service.searchStudentAll(condition);
  }

  /**
   * 受講生詳細の登録を行います
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します")
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
  @Operation(summary = "受講生更新", description = "受講生詳細の更新をします。論理削除も行います。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }


  @Operation(summary = "例外処理", description = "例外処理を行います。")
  @GetMapping("/exceptionStudentList")
  public List<StudentDetail> handleTestException() throws TestException {
    throw new TestException("TestException:" + ErrorMessages.TestException);
  }
}
