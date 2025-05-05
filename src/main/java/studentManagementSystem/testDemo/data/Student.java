package studentManagementSystem.testDemo.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  @Pattern(regexp = "^\\d+$")
  private String studentId;
  private String name;
  private String furigana;
  private String nickname;
  @Email
  private String email;
  private String area;
  private int age;
  private String gender;
  private String remark; // 備考欄
  private boolean isDeleted; // 論理削除

}
