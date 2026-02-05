package studentManagementSystem.testDemo.Controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import studentManagementSystem.testDemo.data.Gender;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.domain.StudentSearchCondition;
import studentManagementSystem.testDemo.service.StudentService;

/**
 * Thymeleafテンプレートを使用したUI用のControllerです
 */
@Controller
public class StudentViewController {

  private final StudentService studentService;

  @Autowired
  public StudentViewController(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * 受講生一覧画面を表示します
   *
   * @param model モデル
   * @return studentList.html
   */
  @GetMapping("/students")
  public String listStudents(Model model) {
    // 検索フォーム用の空オブジェクトを追加
    StudentSearchCondition searchCondition = new StudentSearchCondition();
    searchCondition.setStudent(new Student());
    searchCondition.setStudentCourse(new StudentCourse());

    model.addAttribute("searchCondition", searchCondition);
    model.addAttribute("studentList", studentService.searchStudentList());
    model.addAttribute("courseOptions", studentService.getActiveCourses());
    return "studentList";
  }

  /**
   * 新規登録フォームを表示します
   *
   * @param model モデル
   * @return registerStudent.html
   */
  @GetMapping("/register")
  public String showRegisterForm(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    studentDetail.setStudentCourseList(new ArrayList<>());

    // 初期表示用に1件の空コースを追加し、申込状況を「仮申込」に設定
    StudentCourse initialCourse = new StudentCourse();
    initialCourse.setStudentCourseStatus("仮申込");
    studentDetail.getStudentCourseList().add(initialCourse);

    model.addAttribute("studentDetail", studentDetail);
    model.addAttribute("genderOptions", Gender.values());
    model.addAttribute("courseOptions", studentService.getActiveCourses());
    return "registerStudent";
  }

  /**
   * 新規登録処理を実行します
   *
   * @param studentDetail 受講生詳細
   * @param result バリデーション結果
   * @param model モデル
   * @return 成功時: 一覧へリダイレクト、失敗時: 登録フォーム再表示
   */
  @PostMapping("/register")
  public String registerStudent(
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result,
      Model model) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(error ->
          System.err.println("Validation error: " + error.getDefaultMessage())
      );
      return "registerStudent";
    }

    try {
      studentService.registerStudent(studentDetail);
      return "redirect:/students";
    } catch (Exception e) {
      System.err.println("Registration failed: " + e.getMessage());
      e.printStackTrace();
      model.addAttribute("errorMessage", "登録に失敗しました: " + e.getMessage());
      return "registerStudent";
    }
  }

  /**
   * 受講生詳細画面（更新フォーム）を表示します
   *
   * @param id 受講生ID
   * @param model モデル
   * @return updateStudent.html
   */
  @GetMapping("/student/{id}")
  public String showUpdateForm(@PathVariable("id") String id, Model model) {
    StudentDetail studentDetail = studentService.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    model.addAttribute("genderOptions", Gender.values());
    model.addAttribute("courseOptions", studentService.getActiveCourses());
    return "updateStudent";
  }

  /**
   * 更新処理を実行します
   *
   * @param studentDetail 受講生詳細
   * @param result バリデーション結果
   * @return 成功時: 一覧へリダイレクト、失敗時: 更新フォーム再表示
   */
  @PostMapping("/update")
  public String updateStudent(
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "updateStudent";
    }

    studentService.updateStudent(studentDetail);
    return "redirect:/students";
  }

  /**
   * 検索条件に基づいて受講生を検索します
   *
   * @param searchCondition 検索条件
   * @param model モデル
   * @return studentList.html
   */
  @PostMapping("/students/form-search")
  public String searchStudents(
      @ModelAttribute StudentSearchCondition searchCondition,
      Model model) {

    // 検索実行
    List<StudentDetail> searchResults = studentService.searchStudentWithCondition(searchCondition);

    // フォームの状態を保持するため、検索条件を再度渡す
    model.addAttribute("searchCondition", searchCondition);
    model.addAttribute("studentList", searchResults);
    model.addAttribute("courseOptions", studentService.getActiveCourses());

    // 検索結果が0件の場合のメッセージ
    if (searchResults.isEmpty()) {
      model.addAttribute("message", "検索条件に一致する受講生が見つかりませんでした。");
    }

    return "studentList";
  }
}
