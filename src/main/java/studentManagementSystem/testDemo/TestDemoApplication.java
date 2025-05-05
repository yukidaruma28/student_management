package studentManagementSystem.testDemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
		title = "受講生管理システム",
		description = "受講生情報と受講生のコース情報を管理するシステムです。受講生と受講生コースの情報を登録、読み取り、更新、論理削除ができます。"
))
@SpringBootApplication
@MapperScan("studentManagementSystem.testDemo")

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication.class, args);
	}
}
