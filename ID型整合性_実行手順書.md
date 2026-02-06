# ID型整合性修正 実行手順書

## 📋 概要
- **目的**: `students_courses.studentId`の型を`VARCHAR(36)`から`INT`に変更し、データベースの整合性を向上
- **作成日**: 2026-02-06
- **想定時間**: 30分〜1時間
- **リスクレベル**: 低（ロールバック可能）

---

## ✅ 事前準備チェックリスト

### 必須
- [ ] 本レポート「ID型整合性検証レポート.md」を熟読
- [ ] ステークホルダーへの報告と承認取得
- [ ] バックアップ戦略の確認
- [ ] ロールバック手順の理解

### 推奨
- [ ] ステージング環境での事前テスト
- [ ] メンテナンスウィンドウの確保（本番環境）
- [ ] チーム内での情報共有

---

## 🚀 実行手順

### Step 1: 事前チェック実行

```bash
cd /Users/nakatayukio/Desktop/RaiseTech-Java/studentManegementSystemFork/student_management

# 事前チェックスクリプトの実行
bash /tmp/pre-migration-check.sh
```

**期待される結果:**
```
✓ バックアップ作成完了: backup_id_fix_YYYYMMDD_HHMMSS.sql
✓ すべてのstudentIdが数値形式です
✗ 警告: 孤立レコードが 3 件存在します  ← これを修正する！
```

**⚠️ 孤立レコードが見つかった場合は、Step 2へ進んでください。**

---

### Step 2: 孤立レコードのクリーンアップ

#### 2-1. 孤立レコードの内容確認

```bash
mysql -u root -proot students_management < cleanup_orphaned_data.sql
```

**確認すべき点:**
- 削除される3件のレコード内容を確認
- ビジネス上削除しても問題ないか判断

#### 2-2. クリーンアップ実行

```sql
-- MySQLに接続
mysql -u root -proot students_management

-- cleanup_orphaned_data.sql の内容を実行
SOURCE cleanup_orphaned_data.sql;

-- 検証結果を確認後、コミット
COMMIT;

-- MySQLから退出
EXIT;
```

**期待される結果:**
```
削除されたapplication_statusレコード: 3件
削除されたstudents_coursesレコード: 3件
残存する孤立レコード: 0件
```

---

### Step 3: マイグレーション実行

#### 3-1. 最終確認

```bash
# 孤立レコードがなくなったことを確認
mysql -u root -proot students_management -e "
SELECT COUNT(*) as orphan_count
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;
" | grep -v "Warning"
```

**期待される結果:** `orphan_count: 0`

#### 3-2. マイグレーション実行

```bash
# マイグレーションスクリプトの実行
mysql -u root -proot students_management << 'EOF'

-- トランザクション開始
START TRANSACTION;

-- 型変更
ALTER TABLE students_courses
MODIFY COLUMN studentId INT NOT NULL;

-- 外部キー制約追加
ALTER TABLE students_courses
ADD CONSTRAINT fk_students_courses_student
FOREIGN KEY (studentId) REFERENCES student(studentId)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- 検証
SHOW COLUMNS FROM students_courses WHERE Field = 'studentId';

-- コミット
COMMIT;
EOF
```

**期待される結果:**
```
Field: studentId
Type: int
Null: NO
Key: MUL
```

---

### Step 4: 動作確認

#### 4-1. アプリケーション起動

```bash
cd /Users/nakatayukio/Desktop/RaiseTech-Java/studentManegementSystemFork/student_management

# 既存のプロセスを停止
lsof -ti:8080 | xargs kill -9 2>/dev/null

# アプリケーション起動
./gradlew bootRun &

# 起動完了を待つ（約30秒）
sleep 30
```

#### 4-2. 全機能テスト実行

```bash
bash /tmp/test-all-functions.sh
```

**期待される結果:**
```
=== 機能テスト開始 ===
✓ 一覧画面: OK
✓ 登録画面: OK
✓ 詳細画面: OK
✓ API一覧: OK
✓ API詳細: OK
=== テスト完了 ===
```

#### 4-3. データ整合性確認

```bash
mysql -u root -proot students_management -e "
SELECT
  COUNT(*) as total_courses,
  COUNT(DISTINCT sc.studentId) as unique_students,
  (SELECT COUNT(*) FROM student) as total_students
FROM students_courses sc;
" | grep -v "Warning"
```

**期待される結果:**
- total_courses: 25（孤立レコード削除後）
- unique_students: 親テーブルのstudent数以下
- すべてのJOINが正常に動作

---

### Step 5: パフォーマンステスト（任意）

```bash
mysql -u root -proot students_management << 'EOF'
SET profiling = 1;

-- INT型での検索（変更後）
SELECT * FROM students_courses WHERE studentId = 50;

-- プロファイル確認
SHOW PROFILES;
EOF
```

**期待される結果:**
- クエリ実行時間が改善（特に大量データの場合）

---

## 🔄 ロールバック手順（問題発生時）

### Option 1: トランザクションのロールバック（実行中のみ）

```sql
-- マイグレーション実行中に問題が発生した場合
ROLLBACK;
```

### Option 2: バックアップからの復元

```bash
# 最新のバックアップファイルを確認
ls -lt backup_id_fix_*.sql | head -1

# バックアップから復元（データベース全体）
mysql -u root -proot students_management < backup_id_fix_YYYYMMDD_HHMMSS.sql

# アプリケーション再起動
lsof -ti:8080 | xargs kill -9
./gradlew bootRun
```

### Option 3: マイグレーションスクリプトでロールバック

```bash
mysql -u root -proot students_management << 'EOF'
START TRANSACTION;

-- FK制約削除
ALTER TABLE students_courses
DROP FOREIGN KEY fk_students_courses_student;

-- 型を元に戻す
ALTER TABLE students_courses
MODIFY COLUMN studentId VARCHAR(36);

COMMIT;
EOF
```

---

## 📊 完了確認チェックリスト

### データベース
- [ ] `students_courses.studentId`の型が`INT`に変更されている
- [ ] 外部キー制約`fk_students_courses_student`が作成されている
- [ ] 孤立レコードが0件である
- [ ] 全レコード数が期待通り（削除した3件を除く）

### アプリケーション
- [ ] アプリケーションが正常に起動する
- [ ] 一覧画面が正常に表示される
- [ ] 登録機能が正常に動作する
- [ ] 詳細画面が正常に表示される
- [ ] API（/api/students、/api/students/{id}）が正常に動作する
- [ ] 検索機能が正常に動作する

### ドキュメント
- [ ] 実行ログを保存（実行結果、エラーログなど）
- [ ] ステークホルダーへ完了報告
- [ ] README またはドキュメントに変更内容を記載

---

## 📝 実行ログテンプレート

```
【実行日時】2026-02-06 XX:XX:XX
【実行者】(your name)
【環境】production / staging / local

【Step 1: 事前チェック】
- バックアップファイル: backup_id_fix_YYYYMMDD_HHMMSS.sql
- バックアップサイズ: XX KB
- 孤立レコード数: 3件

【Step 2: クリーンアップ】
- 削除されたstudents_courses: 3件
- 削除されたapplication_status: 3件
- 残存する孤立レコード: 0件

【Step 3: マイグレーション】
- 型変更: ✓ 成功
- FK制約追加: ✓ 成功
- エラー: なし

【Step 4: 動作確認】
- アプリケーション起動: ✓ 成功
- 全機能テスト: ✓ すべてPASS
- データ整合性: ✓ 正常

【Step 5: パフォーマンステスト】
- クエリ実行時間: X.XXX秒 → X.XXX秒（改善率: XX%）

【完了時刻】2026-02-06 XX:XX:XX
【総実行時間】XX分
【結果】✓ 成功
```

---

## 🆘 トラブルシューティング

### 問題1: 「Cannot drop index 'studentId': needed in a foreign key constraint」

**原因:** 既存のFK制約が存在する

**解決策:**
```sql
-- FK制約を確認
SHOW CREATE TABLE students_courses;

-- FK制約を削除してから再実行
ALTER TABLE students_courses DROP FOREIGN KEY existing_fk_name;
```

### 問題2: マイグレーション後にアプリケーションエラー

**原因:** MyBatisの型変換に問題がある（稀）

**解決策:**
1. ログを確認: `tail -f logs/application.log`
2. バックアップから復元
3. 開発チームへエスカレーション

### 問題3: 孤立レコードを削除したくない

**解決策:**
`cleanup_orphaned_data.sql`の代替案セクションを実行し、プレースホルダー受講生を作成

---

## 📞 サポート連絡先

- **技術的質問**: 開発チーム
- **ビジネス判断**: プロジェクトマネージャー
- **緊急時**: システム管理者

---

## 📚 関連ドキュメント

1. `ID型整合性検証レポート.md` - 詳細な分析とリスク評価
2. `migration_id_fix.sql` - マイグレーションスクリプト（詳細版）
3. `cleanup_orphaned_data.sql` - 孤立レコードクリーンアップスクリプト
4. `/tmp/pre-migration-check.sh` - 事前チェックスクリプト
5. `/tmp/test-all-functions.sh` - 機能テストスクリプト

---

**最終更新日**: 2026-02-06
**作成者**: Claude Code
**レビュー者**: (pending)
**承認者**: (pending)
