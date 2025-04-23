package studentManagementSystem.testDemo;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@MapperScan("studentManagementSystem.testDemo")

public class TestDemoApplication {

	@Autowired
	private StudentRepository repository;



	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication.class, args);
	}

	// 全件取得
	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.searchStudent();
	}

	// ID全件取得
	@GetMapping("/studentId")
	public List<Integer> getStudentId() {
		return repository.searchStudentId();
	}

	// 指定したIDの情報を一括取得
	// 動的処理参考文献
	// https://poco-tech.com/posts/spring-boot-introduction/path-variable-annotation/
	// https://annotations-lab.com/pathvariable%E3%81%AE%E4%BD%BF%E3%81%84%E6%96%B9%E3%81%A8%E5%BC%95%E6%95%B0%E3%82%92%E5%BE%B9%E5%BA%95%E8%A7%A3%E8%AA%AC%EF%BC%81%E3%80%90%E5%88%9D%E5%BF%83%E8%80%85%E5%90%91%E3%81%91%E3%80%91/

	// 正規表現で数字のみを入力するようにした
	@GetMapping("/student/{id:[0-9]+}")
	public Student getStudentId(@PathVariable int id) {
		return repository.findStudentId(id);
	}

	// 名前全件取得
	@GetMapping("/studentName")
	public List<String> getStudentName() {
		return repository.searchStudentName();
	}

	// ふりがな全件取得
	@GetMapping("/studentFurigana")
	public List<String> getStudentFurigana() {
		return repository.searchStudentFurigana();
	}

	// ニックネーム全件取得
	@GetMapping("/studentNickname")
	public List<String> getStudentNickname() {
		return repository.searchStudentNickname();
	}

	// メールアドレス全件取得
	@GetMapping("/studentEmail")
	public List<String> getStudentEmail() {
		return repository.searchStudentEmail();
	}

	// 居住地全件取得
	@GetMapping("/studentArea")
	public List<String> getStudentArea() {
		return repository.searchStudentArea();
	}

	// 年齢全件取得
	@GetMapping("/studentAge")
	public List<String> getStudentAge() {
		return repository.searchStudentAge();
	}

	// 性別全件取得
	@GetMapping("/studentGender")
	public List<String> getStudentGender() {
		return repository.searchStudentGender();
	}


	// students_coursesの全件取得
	@GetMapping("/studentsCoursesList")
	public List<StudentsCourses> getStudentsCoursessList() {
		return repository.searchStudentsCourses();
	}
}
