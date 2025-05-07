# 受講生管理システム

## はじめに
このリポジトリはJava学習の一環として作成したものです。
基本的なJavaの学習に求められる一般的な知識を実装しました。

※本リポジトリの利用に関するトラブル等について、開発者は一切の責任を負いかねますのでご了承ください。

## コンセプト
受講生と受講生コース情報の2つをCRUD処理する中で、MySQL、SpringBootなど、Java開発において求められる知識を学べるシステムです。

## 概要
受講生情報：受講生ID, 氏名, 居住地, 年齢, 備考, isDeleted（論理削除）
受講生コース情報：受講生ID, 受講コースID, 受講コース名, 受講開始日, 受講終了予定日

これらの項目のあるDBを作成し、登録・検索・更新・削除機能を実装しています。

## 使用技術 / 開発環境
### 開発言語・バージョン
- Java 21

### フレームワーク・ライブラリ
- Spring Boot 3.4.4
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Validation
- Springdoc OpenAPI (2.8.6)
- MyBatis Spring Boot Starter (3.0.3)
- Apache Commons Lang (3.14.0)
- Lombok

### テスト関連
- Spring Boot Starter Test
- JUnit Platform Launcher

### データベース関連
- MySQL Connector/J (8.0.32)

### ビルド / 設定
- Gradle
- Spring Dependency Management Plugin (1.1.7)

### 開発環境
- IntelliJ IDEA (Community Edition)

## 工夫した点 / 苦労した点 / 学んだこと
### 工夫した点
- Thymeleafフォームにおける非表示フィールド活用（isDeleted）
- MyBatisとの整合性を意識したDTO設計とトランザクション制御

### 苦労した点
- Controlle -> Service -> Repository の一連の流れを理解すること
- テストコード作成でのMockito使用の初学習

### 学んだこと
- MVC分離の重要性とController責務の最小化
- テーブル定義とJavaクラス設計の一致性

## おわりに
このアプリはJavaとSpring Bootの学習の一環として、実際の課題をポートフォリオにしました。
このポートフォリオを通してJavaの知識がついたため、様々なアプリを開発してまいります。
