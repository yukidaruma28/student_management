package studentManagementSystem.testDemo.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;

}
