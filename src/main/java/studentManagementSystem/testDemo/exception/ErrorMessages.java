package studentManagementSystem.testDemo.exception;

public class ErrorMessages {
  public static final String TestException = "TestException エラーが発生しました";

  // 作ったけど、ここまで不要？
  public static final String movedPermanently301 = "301 Moved Permanently 指定したリソースは新しいURLに恒久的に移動しました";
  public static final String found302 = "302 Found 一時的にリソースのURLが変更されています";
  public static final String seeOther303 = "303 See Other 別のURLでリソースを取得してください";
  public static final String temporaryRedirect307 = "307 Temporary Redirect 別のURLにリクエストを再送信してください";

  public static final String badRequest400 = "400 Bad Request リクエストの構文ミスです";
  public static final String unauthorized401 = "401 Unauthorized 認証情報が不足しています";
  public static final String forbidden403 = "403 Forbidden アクセスが拒否されました";
  public static final String notFound404 = "404 Not Found リソースが見つかりません";
  public static final String methodNotAllowed405 = "405 Method Not Allowed 許可されていないHTTPメソッドです";

  public static final String internalServerError500 = "500 Internal Server Error サーバー内部で予期しないエラーが発生しました";
  public static final String serviceUnavailable503 = "503 Service Unavailable 現在サービスを利用できません（メンテナンス中またはアクセス集中）";
  public static final String gatewayTimeout504 = "504 Gateway Timeout 上流サーバーとの通信に失敗しました（タイムアウト）";
}
