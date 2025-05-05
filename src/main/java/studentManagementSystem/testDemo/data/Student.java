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
  @Schema(description = "受講生IDを自動付与", example = "1")
  @Pattern(regexp = "^\\d+$", message = "整数のみ")
  private String studentId;

  @Schema(description = "氏名", example = "山田太郎")
  private String name;

  @Schema(description = "ふりがな", example = "やまだたろう")
  private String furigana;

  @Schema(description = "ニックネーム", example = "タロ")
  private String nickname;

  @Schema(description = "メールアドレス", example = "taro@example.com")
  @Email
  private String email;

  @Schema(description = "地域", example = "兵庫")
  private String area;

  @Schema(description = "年齢", example = "20")
  private int age;

  @Schema(description = "性別", example = "男性")
  private String gender;

  @Schema(description = "備考欄", example = "未経験転職するために、東京へ上京予定。")
  private String remark;

  @Schema(description = "論理削除", example = "1")
  private boolean isDeleted; // 論理削除

}
