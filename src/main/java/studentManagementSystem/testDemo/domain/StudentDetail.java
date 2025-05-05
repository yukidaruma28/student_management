package studentManagementSystem.testDemo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Schema(description = "受講生")
  @Valid
  private Student student;

  @Schema(description = "受講生コース情報")
  @Valid
  private List<StudentCourse> studentCourseList;

}
