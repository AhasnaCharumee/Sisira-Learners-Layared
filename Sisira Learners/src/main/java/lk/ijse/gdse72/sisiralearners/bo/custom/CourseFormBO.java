package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseFormBO extends SuperBO {
    ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException;
    boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;
    boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;
    boolean deleteCourse(String courseId) throws SQLException, ClassNotFoundException;
    boolean existCourse(String id) throws SQLException, ClassNotFoundException;
    String getNextCourseId() throws SQLException, ClassNotFoundException;
}
