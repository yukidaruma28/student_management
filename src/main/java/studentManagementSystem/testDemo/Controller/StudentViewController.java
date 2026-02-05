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
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;
import studentManagementSystem.testDemo.domain.StudentDetail;
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
    List<StudentDetail> studentList = studentService.searchStudentList();
    model.addAttribute("studentList", studentList);
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
    // 初期表示用に1件の空コースを追加
    studentDetail.getStudentCourseList().add(new StudentCourse());
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  /**
   * 新規登録処理を実行します
   *
   * @param studentDetail 受講生詳細
   * @param result バリデーション結果
   * @return 成功時: 一覧へリダイレクト、失敗時: 登録フォーム再表示
   */
  @PostMapping("/register")
  public String registerStudent(
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "registerStudent";
    }

    studentService.registerStudent(studentDetail);
    return "redirect:/students";
  }

  /**
   * 受講生詳細画面（更新フォーム）を表示します
   *
   * @param id 受講生ID
   * @param model モデル
   * @return updateStudent.html
   */
  @GetMapping("/students/{id}")
  public String showUpdateForm(@PathVariable("id") String id, Model model) {
    StudentDetail studentDetail = studentService.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
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
}
