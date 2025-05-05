package studentManagementSystem.testDemo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
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