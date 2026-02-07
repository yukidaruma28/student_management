-- ========================================
-- ID型整合性修正マイグレーション
-- 作成日: 2026-02-06
-- 目的: students_courses.studentId を VARCHAR(36) から INT に変更
-- ========================================

-- 【重要】実行前に必ずバックアップを取得してください:
-- mysqldump -u root -proot students_management > backup_id_fix_$(date +%Y%m%d_%H%M%S).sql

-- ========================================
-- 事前検証
-- ========================================

-- 1. 現在の型確認
SHOW COLUMNS FROM students_courses WHERE Field = 'studentId';
-- 期待値: Type = varchar(36)

-- 2. データ整合性確認（数値以外のデータがないか）
SELECT
  studentId,
  COUNT(*) as count
FROM students_courses
WHERE studentId NOT REGEXP '^[0-9]+$'
GROUP BY studentId;
-- 期待値: Empty set (0 rows)

-- 3. 親テーブルとの整合性確認
SELECT
  sc.studentId as fk_value,
  s.studentId as pk_value
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;
-- 期待値: Empty set (孤立したレコードなし)

-- ========================================
-- マイグレーション実行
-- ========================================

START TRANSACTION;

-- Step 1: 外部キー制約がある場合は削除（既存制約確認）
SELECT
  CONSTRAINT_NAME,
  TABLE_NAME,
  COLUMN_NAME,
  REFERENCED_TABLE_NAME,
  REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'students_courses'
  AND COLUMN_NAME = 'studentId'
  AND CONSTRAINT_NAME != 'PRIMARY';
-- 既存のFK制約があれば以下で削除:
-- ALTER TABLE students_courses DROP FOREIGN KEY fk_constraint_name;

-- Step 2: 型変更（VARCHAR(36) → INT）
ALTER TABLE students_courses
MODIFY COLUMN studentId INT NOT NULL;

-- Step 3: 外部キー制約を追加（データ整合性強化）
ALTER TABLE students_courses
ADD CONSTRAINT fk_students_courses_student
FOREIGN KEY (studentId) REFERENCES student(studentId)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Step 4: インデックス確認（パフォーマンス向上）
SHOW INDEX FROM students_courses WHERE Column_name = 'studentId';
-- FK作成時に自動的にインデックスが作成されるはず

-- ========================================
-- 事後検証
-- ========================================

-- 1. 型変更確認
SHOW COLUMNS FROM students_courses WHERE Field = 'studentId';
-- 期待値: Type = int

-- 2. FK制約確認
SELECT
  CONSTRAINT_NAME,
  TABLE_NAME,
  COLUMN_NAME,
  REFERENCED_TABLE_NAME,
  REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'students_courses'
  AND COLUMN_NAME = 'studentId'
  AND CONSTRAINT_NAME != 'PRIMARY';
-- 期待値: fk_students_courses_student が表示される

-- 3. データ件数確認
SELECT
  'students_courses' as table_name,
  COUNT(*) as record_count
FROM students_courses
UNION ALL
SELECT
  'student',
  COUNT(*)
FROM student;
-- 期待値: レコード数が変わっていないこと

-- 4. データサンプル確認
SELECT
  sc.students_courses_id,
  sc.studentId,
  s.full_name,
  sc.select_courses
FROM students_courses sc
INNER JOIN student s ON sc.studentId = s.studentId
ORDER BY sc.students_courses_id DESC
LIMIT 5;
-- 期待値: JOINが正常に動作し、データが表示される

-- ========================================
-- コミット判断
-- ========================================

-- すべての検証がPASSしたらコミット:
-- COMMIT;

-- 問題があればロールバック:
-- ROLLBACK;

-- ========================================
-- ロールバックスクリプト（緊急時）
-- ========================================

/*
START TRANSACTION;

-- FK制約削除
ALTER TABLE students_courses
DROP FOREIGN KEY fk_students_courses_student;

-- 型を元に戻す
ALTER TABLE students_courses
MODIFY COLUMN studentId VARCHAR(36);

COMMIT;
*/

-- ========================================
-- パフォーマンステスト（任意）
-- ========================================

-- 変更前のクエリ実行時間を測定（バックアップDBで実行）
-- SET profiling = 1;
-- SELECT * FROM students_courses WHERE studentId = '50';
-- SHOW PROFILES;

-- 変更後のクエリ実行時間を測定
-- SET profiling = 1;
-- SELECT * FROM students_courses WHERE studentId = 50;
-- SHOW PROFILES;

-- 期待値: INT検索の方が高速（特にデータ量が多い場合）

-- ========================================
-- 完了チェックリスト
-- ========================================

/*
[ ] バックアップ取得完了
[ ] ステージング環境でテスト完了
[ ] 事前検証すべてPASS
[ ] マイグレーション実行完了
[ ] 事後検証すべてPASS
[ ] アプリケーション動作確認完了
[ ] パフォーマンステスト完了（任意）
[ ] ドキュメント更新完了
[ ] ステークホルダーへ報告完了
*/
