package studentManagementSystem.testDemo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
import studentManagementSystem.testDemo.repository.StudentRepository;

@Service
public class StudentService {
  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

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


  public List<StudentsCourses> searchStudentsCoursessList() {
    return repository.searchStudentsCourses();
  }


}
