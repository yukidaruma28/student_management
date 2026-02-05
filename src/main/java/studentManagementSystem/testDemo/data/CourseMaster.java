package studentManagementSystem.testDemo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "コースマスター")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseMaster {

  @Schema(description = "コースID", example = "1")
  private Integer courseId;

  @Schema(description = "コース名", example = "Javaコース")
  private String courseName;

  @Schema(description = "説明", example = "Java基礎から応用まで")
  private String description;

  @Schema(description = "有効フラグ", example = "true")
  private Boolean isActive;
}
