-- ========================================
-- 孤立レコードクリーンアップスクリプト
-- 作成日: 2026-02-06
-- 目的: 親テーブル（student）に存在しないstudentIdを持つレコードを削除
-- ========================================

-- 【重要】このスクリプトはmigration_id_fix.sqlの実行前に実行してください

-- ========================================
-- 孤立レコード確認
-- ========================================

-- 1. 孤立したstudents_coursesレコードの確認
SELECT
  '孤立したstudents_coursesレコード' as 説明,
  COUNT(*) as 件数
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;

-- 詳細表示
SELECT
  sc.students_courses_id,
  sc.studentId,
  sc.select_courses,
  sc.start_date,
  sc.student_course_status
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL
ORDER BY sc.students_courses_id;

-- 2. 対応するapplication_statusレコードの確認
SELECT
  '孤立コースに紐づくapplication_statusレコード' as 説明,
  COUNT(*) as 件数
FROM application_status a
INNER JOIN students_courses sc ON a.students_courses_id = sc.students_courses_id
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;

-- 詳細表示
SELECT
  a.application_status_id,
  a.students_courses_id,
  a.status,
  sc.studentId as orphan_student_id,
  sc.select_courses
FROM application_status a
INNER JOIN students_courses sc ON a.students_courses_id = sc.students_courses_id
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL
ORDER BY a.application_status_id;

-- ========================================
-- クリーンアップ実行
-- ========================================

START TRANSACTION;

-- Step 1: 削除前のレコード数を記録
SET @before_courses = (SELECT COUNT(*) FROM students_courses);
SET @before_status = (SELECT COUNT(*) FROM application_status);

SELECT
  '削除前のレコード数' as 説明,
  @before_courses as students_courses,
  @before_status as application_status;

-- Step 2: 孤立したコースに紐づくapplication_statusを削除
DELETE a
FROM application_status a
INNER JOIN students_courses sc ON a.students_courses_id = sc.students_courses_id
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;

SELECT
  '削除されたapplication_statusレコード' as 説明,
  ROW_COUNT() as 件数;

-- Step 3: 孤立したstudents_coursesレコードを削除
DELETE sc
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;

SELECT
  '削除されたstudents_coursesレコード' as 説明,
  ROW_COUNT() as 件数;

-- Step 4: 削除後のレコード数を確認
SET @after_courses = (SELECT COUNT(*) FROM students_courses);
SET @after_status = (SELECT COUNT(*) FROM application_status);

SELECT
  '削除後のレコード数' as 説明,
  @after_courses as students_courses,
  @after_status as application_status;

-- Step 5: データ整合性確認
SELECT
  '残存する孤立レコード' as 説明,
  COUNT(*) as 件数
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;

-- 期待値: 0件

-- ========================================
-- コミット判断
-- ========================================

-- 検証がすべてPASSしたらコミット:
-- COMMIT;

-- 問題があればロールバック:
-- ROLLBACK;

-- ========================================
-- クリーンアップ完了チェックリスト
-- ========================================

/*
[ ] 削除されるレコードの内容を確認済み
[ ] ビジネス上削除して問題ないことを確認済み
[ ] トランザクション内で実行済み
[ ] 削除件数が期待通り（students_courses: 3件, application_status: 3件）
[ ] 残存する孤立レコード: 0件
[ ] コミット完了
[ ] migration_id_fix.sql の実行準備完了
*/

-- ========================================
-- 代替案: 孤立レコード修復（削除したくない場合）
-- ========================================

/*
-- もし削除せずに修復したい場合は以下を実行:
-- （ただし、studentId 18, 19, 20 の受講生データが必要）

START TRANSACTION;

-- プレースホルダーの受講生データを作成
INSERT INTO student (studentId, full_name, furigana, email, city_name, age, gender)
VALUES
  (18, '削除済み受講生18', 'サクジョズミ18', 'deleted18@example.com', '不明', 0, 'その他'),
  (19, '削除済み受講生19', 'サクジョズミ19', 'deleted19@example.com', '不明', 0, 'その他'),
  (20, '削除済み受講生20', 'サクジョズミ20', 'deleted20@example.com', '不明', 0, 'その他');

-- データ整合性確認
SELECT COUNT(*) as remaining_orphans
FROM students_courses sc
LEFT JOIN student s ON CAST(sc.studentId AS UNSIGNED) = s.studentId
WHERE s.studentId IS NULL;
-- 期待値: 0件

COMMIT;
*/
