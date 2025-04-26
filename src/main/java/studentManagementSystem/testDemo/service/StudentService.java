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

  public List<Integer> getStudentId() {
    return repository.searchStudentId();
  }

  public Student getStudentId(@PathVariable int id) {
    return repository.findStudentId(id);
  }

  public List<String> getStudentName() {
    return repository.searchStudentName();
  }

  public List<String> getStudentFurigana() {
    return repository.searchStudentFurigana();
  }

  public List<String> getStudentNickname() {
    return repository.searchStudentNickname();
  }

  public List<String> getStudentEmail() {
    return repository.searchStudentEmail();
  }

  public List<String> getStudentArea() {
    return repository.searchStudentArea();
  }

  public List<String> getStudentAge() {
    return repository.searchStudentAge();
  }

  public List<String> getStudentGender() {
    return repository.searchStudentGender();
  }


  public List<StudentsCourses> searchStudentsCoursessList() {
    return repository.searchStudentsCourses();
  }
}
