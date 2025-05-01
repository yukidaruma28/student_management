# 28_Thymeleafを使ったPOST処理 より課題が難しくなった＆PR用の思考メモをまとめます

## 課題① 新規受講生情報を登録する処理を実装する のメモです
  1.JDBCのお約束と書き方例（.executeQuery()を考えたが、思ったより面倒そうなので却下）
    // https://qiita.com/shuyam/items/05885b7b24a2e640bb27
  2.SpringBootでDB操作（.save()で簡単に実装できそうなので、こっちで実装する）
    // https://qiita.com/bbbks9/items/3a9308e8e0821fcb64e6
  3.Mybatisでは.save()が使えないとChatGPTに言われたため、改めて検索すると、@Insertをつかって、Java上でSQLが操作できることを知る
  4.@PostMappingと@Insertの違いがわからず、調査した結果、StudentRepositoryに@Insertを作成して、SQLを操作できることに気づく
    そこから、「フォーム入力→@PostMappingにて情報をキャッチ→@InsertでSQLに登録する」という一連の流れができることを思いつく
  5.@Insert()の()内に、SQLのコマンドを入力すれば、Java上でSQLが操作できることが判明。一旦名前だけでやってみる
    Insertの基本構文 INSERT INTO テーブル名 (列名1, 列名2, 列名3) VALUES (値1, 値2, 値3)

     // 色々なSELECT文のまとめ！IN句、LIKE句やInsert文も！: https://www.higutthiengineer.com/2024/06/06/springbootmybatiselectinlike/
     // [SQL] INSERT文の基本構文: https://qiita.com/minhee/items/fee4a575e2067bbbcdcd

## 課題② コース情報も一緒に登録できるように実装する。コースは単体でOK。
### 基本方針
コース情報の確認は /studentsCoursesList でOK。
  - HTMLは一緒で良いから、course_name、course_start_date、end_dateの3つを、HTMLに追加する
    そうすることで、コース情報の入力をできるようにする
  - 名前やメアドの登録としては、` StudentDetail.student.name ` という値？になる
  - コースの登録情報としては、 ` StudentDetail.studentsCourses.courseName ` という値になる
  - なので、この登録情報を引数で渡して、/studentListで受講生コース一覧も一緒に出したらいいんじゃね？

### 基本方針を踏まえて、やること
  1. HTMLの改造
    registerStudent -> course_name、course_start_date、end_dateの3つを、追加する
    studentList -> course_name、course_start_date、end_dateの3つを、追加する
  2. 登録情報をSQLまで引数で渡して、登録する一連のコーディング
  3. /studentListで受講生コース一覧も一緒に出す
