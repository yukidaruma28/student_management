package studentManagementSystem.testDemo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
import studentManagementSystem.testDemo.repository.StudentRepository;

@Service
public class StudentService {
  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    List<Student> studentList = repository.searchStudent();

    // 課題① 24_Read処理のServiceとController部分を実装
    // 絞り込みを行う。年齢が30代の人のみを抽出する。
    // 抽出したリストをControllerに返す。
    List<Student> filteredList = studentList.stream()
        .filter(i -> i.getAge() >= 30 && i.getAge() <= 39)
        .toList();

    return filteredList;
  }

//  public List<Student> searchStudentList() {
//    // 検索処理 Repositoryの情報をServiceで使えるように、studentListという変数につめた
//    List<Student> studentList = repository.searchStudent();
//
//    // 課題① 24_Read処理のServiceとController部分を実装
//    // 絞り込みを行う。年齢が30代の人のみを抽出する。
//    // 抽出したリストをControllerに返す。
//    List<Student> filteredList = studentList.stream()
//        .filter(i -> i.getAge() >= 30 && i.getAge() <= 39)
//        .toList();
//
//    return filteredList;
//  }

  public List<Integer> searchStudentId() {
    return repository.searchStudentId();
  }

  public Student getStudentId(@PathVariable int id) {
    return repository.searchStudentById(id);
  }

  public Student searchStudentById(int id) {
    return repository.searchStudentById(id);
  }

  public List<String> searchStudentName() {
    return repository.searchStudentName();
  }

  public List<String> searchStudentFurigana() {
    return repository.searchStudentFurigana();
  }

  public List<String> searchStudentNickname() {
    return repository.searchStudentNickname();
  }

  public List<String> searchStudentEmail() {
    return repository.searchStudentEmail();
  }

  public List<String> searchStudentArea() {
    return repository.searchStudentArea();
  }

  public List<String> searchStudentAge() {
    return repository.searchStudentAge();
  }

  public List<String> searchStudentGender() {
    return repository.searchStudentGender();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCoursesList();
  }

  public List<StudentsCourses> searchStudentsCoursessList() {
    return repository.searchStudentsCoursesList();
  }

//課題②のために、コメントアウト
//  public List<StudentsCourses> searchStudentsCoursesList() {
//    // 課題② 24_Read処理のServiceとController部分を実装
//    // 絞り込み検索で「Java基礎コース」のコース情報のみを抽出する。
//    // 抽出したリストをControllerに返す。
//
//    // 検索処理 Repositoryの情報をServiceで使えるように、searchStudentsCoursesという変数につめた
//    List<StudentsCourses> searchStudentsCourses = repository.searchStudentsCourses();
//
//    // 絞り込み処理
//    List<StudentsCourses> filteredStudentsCourses = searchStudentsCourses.stream()
//        .filter(i -> i.getCourseName().contains("Java基礎コース"))
//        .toList();
//
//    return filteredStudentsCourses;
//  }
}
