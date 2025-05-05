package studentManagementSystem.testDemo.data;

import jakarta.validation.constraints.Pattern;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourse {

  @Pattern(regexp = "^\\d+$")
  private String studentsCoursesId;

  @Pattern(regexp = "^\\d+$")
  private String studentId;
  private String courseName;
  private Timestamp startDate;
  private Timestamp endDate;

}