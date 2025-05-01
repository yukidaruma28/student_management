package studentManagementSystem.testDemo.Controller;

import org.apache.ibatis.annotations.Insert;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import studentManagementSystem.testDemo.Controller.converter.StudentConverter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
import studentManagementSystem.testDemo.domain.StudentDetail;
import studentManagementSystem.testDemo.repository.StudentRepository;
import studentManagementSystem.testDemo.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  // 全件取得
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }



  // ID全件取得
  @GetMapping("/studentId")
  public List<Integer> getStudentId() {
    return service.searchStudentId();
  }

  // 指定したIDの情報を一括取得
  // 動的処理参考文献
  // https://poco-tech.com/posts/spring-boot-introduction/path-variable-annotation/
  // https://annotations-lab.com/pathvariable%E3%81%AE%E4%BD%BF%E3%81%84%E6%96%B9%E3%81%A8%E5%BC%95%E6%95%B0%E3%82%92%E5%BE%B9%E5%BA%95%E8%A7%A3%E8%AA%AC%EF%BC%81%E3%80%90%E5%88%9D%E5%BF%83%E8%80%85%E5%90%91%E3%81%91%E3%80%91/

  // 正規表現で数字のみを入力するようにした
  @GetMapping("/student/{id:[0-9]+}")
  public Student getStudentId(@PathVariable int id) {
    return service.searchStudentById(id);
  }

  // 名前全件取得
  @GetMapping("/studentName")
  public List<String> getStudentName() {
    return service.searchStudentName();
  }

  // ふりがな全件取得
  @GetMapping("/studentFurigana")
  public List<String> getStudentFurigana() {
    return service.searchStudentFurigana();
  }

  // ニックネーム全件取得
  @GetMapping("/studentNickname")
  public List<String> getStudentNickname() {
    return service.searchStudentNickname();
  }

  // メールアドレス全件取得
  @GetMapping("/studentEmail")
  public List<String> getStudentEmail() {
    return service.searchStudentEmail();
  }

  // 居住地全件取得
  @GetMapping("/studentArea")
  public List<String> getStudentArea() {
    return service.searchStudentArea();
  }

  // 年齢全件取得
  @GetMapping("/studentAge")
  public List<String> getStudentAge() {
    return service.searchStudentAge();
  }

  // 性別全件取得
  @GetMapping("/studentGender")
  public List<String> getStudentGender() {
    return service.searchStudentGender();
  }


  // students_coursesの全件取得
  @GetMapping("/studentsCoursesList")
  public List<StudentsCourses> getStudentsCoursessList() {
    return service.searchStudentsCoursessList();
  }


  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping ("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) { // エラーが起きたときに、元の画面に戻る処理
      System.out.println("エラーが発生しています。"); // 文字としてもわかるように追加
      return ("registerStudent");
    }
    // 28_Thymeleafを使ったPOST処理
    // 課題① 新規受講生情報を登録する処理を実装する。
    // 最終的に /studentList で確認できるようにする。

    service.registerStudent(studentDetail);


    // 28_Thymeleafを使ったPOST処理
    // 課題② コース情報も一緒に登録できるように実装する。コースは単体でOK。
    // コース情報の確認は /studentsCoursesList でOK。




    return "redirect:/studentList";
  }
}

