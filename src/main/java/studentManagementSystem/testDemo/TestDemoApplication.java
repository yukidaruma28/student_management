package studentManagementSystem.testDemo;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import studentManagementSystem.testDemo.data.Student;
import studentManagementSystem.testDemo.data.StudentsCourses;
import studentManagementSystem.testDemo.repository.StudentRepository;


@SpringBootApplication
@MapperScan("studentManagementSystem.testDemo")

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication.class, args);
	}
}
