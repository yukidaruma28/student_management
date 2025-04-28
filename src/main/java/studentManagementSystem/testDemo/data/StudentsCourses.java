package studentManagementSystem.testDemo.data;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private int studentsCoursesId;
  private int id;
  private String courseName;
  private Timestamp startDate;
  private Timestamp endDate;

}