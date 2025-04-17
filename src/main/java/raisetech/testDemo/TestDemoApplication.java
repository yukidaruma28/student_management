package raisetech.testDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication.class, args);
	}

	@GetMapping("/Hello")
	public String test(){
		return "test!";
	}
}
