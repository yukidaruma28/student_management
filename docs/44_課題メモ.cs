# 44_検索条件の追加と申込み状況機能の追加

## 要件の追加
### コースに対して申込状況がわかること
- 仮申込・本申込・受講中・受講終了の4つの状態が各コース単位でわかること
- コースと申込状況の関係は 1:1 である

-> 申込状況テーブルを作る感じ
- 申込状況の定義
  ID
  受講生コース情報のID
  申込状況

// 申込状況テーブルの作成 -> student_courses に項目追加するだけでよくね？ 受講生1名に対して、複数コース受講する場合、IDの関係はどうなる？
// registerStudent.html updateStudent.html studentRepository.xmlに対して、申込状況の追加
// 登録時は仮申込を確定で入れておく
// 更新では、本申込〜受講終了が選択できるようにする

// 先にtest環境だけ、studentCourseStatusをテーブルに追加しておく。本番テーブルにはまだ追加しない

申込状況：studentCourseStatus

ALTER TABLE students_courses ADD student_course_status varchar(36);


### 検索時は検索条件を指定して、様々な検索条件で受講生を検索できること
全件検索を残しつつ、　検索条件を指定できる

参考URL
// https://job-info.hateblo.jp/?page=1723206862


