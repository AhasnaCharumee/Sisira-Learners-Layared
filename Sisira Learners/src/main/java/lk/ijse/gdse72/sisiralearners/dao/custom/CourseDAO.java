package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO extends CrudDAO<Course> {
    List<String> getActiveCourses() throws SQLException;
    String getCourseNameByStudentId(String studentId) throws SQLException;
    String getCourseId(String courseName) throws SQLException;
    double getCourseFee(String courseName) throws SQLException;

}
