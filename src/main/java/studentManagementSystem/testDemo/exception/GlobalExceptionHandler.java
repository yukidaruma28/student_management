package studentManagementSystem.testDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  // 課題36 ExceptionHandlerをController以外でも動くように、classで例外処理ができる
  // Controllerとかで、パッケージhandlerを作り、 ExceptionHandlingをする、専用のclassを作って、例外処理を実装する
  // 例外を発生させるように専用のメソッドを作る -> テキトーなclassを作る -> 例外処理が継続してできるようにする

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
