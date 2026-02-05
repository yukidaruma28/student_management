package studentManagementSystem.testDemo.data;

public enum Gender {
  MALE("男性"),
  FEMALE("女性"),
  OTHER("その他");

  private final String displayName;

  Gender(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  /**
   * 表示名から逆引き
   *
   * @param displayName 表示名
   * @return 対応するGender、見つからない場合はnull
   */
  public static Gender fromDisplayName(String displayName) {
    for (Gender gender : values()) {
      if (gender.displayName.equals(displayName)) {
        return gender;
      }
    }
    return null;
  }
}
