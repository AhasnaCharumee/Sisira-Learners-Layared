package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.CourseDAO;
import lk.ijse.gdse72.sisiralearners.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    public ArrayList<Course> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Course");
        ArrayList<Course> courses = new ArrayList<>();

        while (rst.next()) {
            Course course = new Course(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            courses.add(course);
        }

        return courses;
    }

    public List<String> getActiveCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM Course WHERE status = 'Active'");
        while (resultSet.next()) {
            courses.add(resultSet.getString("name"));
        }
        return courses;
    }

    public double getCourseFee(String courseName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT price FROM Course WHERE name = ?", courseName);
        if (resultSet.next()) {
            return resultSet.getDouble("price");
        }
        return 0.0;
    }

    public boolean save(Course course) throws SQLException {
        return SQLUtil.execute("INSERT INTO Course VALUES (?,?,?,?,?)",
                course.getCourse_id(),
                course.getStatus(),
                course.getName(),
                course.getDuration(),
                course.getPrice()
        );
    }

    public boolean update(Course course) throws SQLException {
        return SQLUtil.execute("UPDATE Course SET status=?, name=?, duration=?, price=? WHERE course_id=?",
                course.getStatus(),
                course.getName(),
                course.getDuration(),
                course.getPrice(),
                course.getCourse_id()
        );
    }

    public boolean delete(String courseId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Course WHERE course_id=?", courseId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT course_id FROM Course WHERE course_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT course_id FROM Course ORDER BY course_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }


    public String getCourseId(String courseName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT course_id FROM Course WHERE name = ?", courseName);
        if (resultSet.next()) {
            return resultSet.getString("course_id");
        }
        return null;
    }
    public String getCourseNameByStudentId(String studentId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT c.name FROM Course c JOIN StudentRegistration sr ON c.course_id = sr.course_id WHERE sr.student_id = ?", studentId);
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        return null;
    }
}