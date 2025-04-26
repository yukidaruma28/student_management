package studentManagementSystem.testDemo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// studentテーブルで持つカラムをここでもたせる
public class Student {

  private int id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String gender;

}