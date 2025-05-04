package studentManagementSystem.testDemo.data;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourse {

  private String studentsCoursesId;
  private String studentId;
  private String courseName;
  private Timestamp startDate;
  private Timestamp endDate;

}