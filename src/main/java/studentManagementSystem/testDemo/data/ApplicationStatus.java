package studentManagementSystem.testDemo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "申込状況")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatus {

  @Schema(description = "申込状況ID", example = "1")
  @Pattern(regexp = "^\\d+$")
  private String applicationStatusId;

  @Schema(description = "受講生コース情報ID", example = "1")
  @Pattern(regexp = "^\\d+$")
  private String studentsCoursesId;

  @Schema(description = "申込状況", example = "仮申込")
  private String status;
}
