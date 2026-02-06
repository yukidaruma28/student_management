package studentManagementSystem.testDemo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import studentManagementSystem.testDemo.data.ApplicationStatus;

@Mapper
public interface ApplicationStatusRepository {

  List<ApplicationStatus> findByStudentsCoursesId(String studentsCoursesId);

  void insert(ApplicationStatus applicationStatus);

  void update(ApplicationStatus applicationStatus);

  void deleteByStudentsCoursesId(String studentsCoursesId);
}
