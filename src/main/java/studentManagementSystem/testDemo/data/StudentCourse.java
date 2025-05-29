package studentManagementSystem.testDemo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentCourse {

  @Schema(description = "受講生コースIDを自動付与", example = "1")
  @Pattern(regexp = "^\\d+$")
  private String studentsCoursesId;

  @Schema(description = "受講生ID", example = "1")
  @Pattern(regexp = "^\\d+$")
  private String studentId;

  @Schema(description = "受講コース名", example = "Javaコース")
  private String courseName;

  @Schema(description = "受講開始日", example = "2025-08-01 00:00:00")
  private Timestamp startDate;

  @Schema(description = "受講終了予定日", example = "2026-08-01 00:00:00")
  private Timestamp endDate;

  @Schema(description = "申込状況", example = "仮申込")
  private String studentCourseStatus;

}
