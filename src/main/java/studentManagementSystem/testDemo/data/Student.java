package studentManagementSystem.testDemo.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// studentテーブルで持つカラムをここでもたせる
public class Student {

  private String studentId;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String gender;
  private String remark; // 備考欄
  private boolean isDeleted; // 論理削除 削除フラグ

}

// チャレンジ課題
// ALTER TABLE student ADD remark VARCHAR(255) AFTER gender;
// ALTER TABLE student ADD isDeleted VARCHAR(255) AFTER remark;