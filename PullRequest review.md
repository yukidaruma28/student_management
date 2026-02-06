仕様
  受講生情報の定義
    ID
      id
    名前（フルネーム）
      full_name
    フリガナ
      furigana
    ニックネーム
      nickname
    メールアドレス
      email
    住んでいる地域（市区町村）
      city_name
    年齢
      age
    性別（トランスジェンダー考慮）
      gender
        https://qiita.com/aoshirobo/items/32deb45cb8c8b87d65a4


  受講生コース情報の定義
    ID
      students_courses_id
    受講生情報のID
      id
    コース名
      select_courses
    受講開始日 → 日付型
      start_date
    受講終了予定日 → 日付型
      end_date
  
  申込状況の定義
    ID
    受講生コース情報のID
    申込状況

  要件
    受講生の個人情報が管理できること
      受講生情報を検索、登録、更新、削除できること
    受講生が何のコースを受けたかを管理できること
      受講生コース情報を検索、登録、更新、削除できること
    受講生と受講生コースは関連あり、複数コース受講を考慮すること
      → 1:Nの関係

    ※ コースに対して申込状況がわかること
    各コースの申し込み状況が分かる機能
      仮申し込み、本申し込み、受講中、受講終了の4つの状態が各コース単位でわかること
      コースと申込状況は1:1の関係
    
    ※ 検索時は検索条件を指定して、様々な検索条件で受講生を検索できること
    登録するときは、仮申し込みを確定で入れておく
    更新するときは、仮申し込み〜受講終了が選択できるようにする

  DB設計
    テーブル構成
    受講生
    受講生コース

受講生情報：students
受講生コース情報：students_courses

# 実装履歴

## 2026-02-05: 性別とコース名のプルダウン化実装

### 実装内容
1. **性別（Gender）をEnumで実装**
   - Gender.java を新規作成（MALE/FEMALE/OTHER）
   - Student.java の gender フィールドを String → Gender 型に変更
   - MyBatis が自動的に Enum ↔ String 変換を実行
   - 表示名（男性、女性、その他）とEnum値を分離

2. **コース名をマスターテーブルで実装**
   - course_master テーブルを新規作成
   - CourseMaster.java エンティティを新規作成
   - Repository、Mapper、Service に getActiveCourses() メソッドを追加
   - 14種類のコースをマスターデータとして登録

3. **HTML テンプレートの修正**
   - registerStudent.html: 性別とコース名を input → select に変更
   - updateStudent.html: 性別とコース名を input → select に変更
   - studentList.html: 性別表示を gender.displayName に修正

4. **Controller の修正**
   - StudentViewController に genderOptions と courseOptions を Model に追加
   - 登録・更新フォーム両方で選択肢を提供

5. **テストコードの更新**
   - StudentRepositoryTest: 全ての Student インスタンスで Gender enum を使用
   - StudentServiceTest: Gender enum に対応
   - StudentConverterTest: Gender enum に対応

### コンパイル確認
- メインコード: ✅ 正常にコンパイル完了
- テストコード: ✅ 正常にコンパイル完了

### 既知の問題
- Gradle 8.13 が JDK 24 と互換性がないため、`./gradlew test` が実行不可
- 解決策: Java 17 または 21 にダウングレード、もしくは Gradle 8.14 以降にアップグレード

### データベース互換性
- **性別**: 既存データは "男性"、"女性"、"その他" として保存されており、そのまま互換性あり
- **コース名**: 既存の students_courses テーブルは変更なし。course_master は新規追加のみ

