package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.CourseFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.CourseDAO;
import lk.ijse.gdse72.sisiralearners.dto.CourseDTO;
import lk.ijse.gdse72.sisiralearners.entity.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseFormBOImpl implements CourseFormBO {

    private CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        ArrayList<Course> courses = courseDAO.getAllData();
        ArrayList<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courses) {
            courseDTOs.add(new CourseDTO(
                    course.getCourse_id(),
                    course.getStatus(),
                    course.getName(),
                    course.getDuration(),
                    course.getPrice()
            ));
        }
        return courseDTOs;
    }

    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        Course course = new Course(
                courseDTO.getCourse_id(),
                courseDTO.getStatus(),
                courseDTO.getName(),
                courseDTO.getDuration(),
                courseDTO.getPrice()
        );
        return courseDAO.save(course);
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        Course course = new Course(
                courseDTO.getCourse_id(),
                courseDTO.getStatus(),
                courseDTO.getName(),
                courseDTO.getDuration(),
                courseDTO.getPrice()
        );
        return courseDAO.update(course);
    }

    @Override
    public boolean deleteCourse(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.delete(courseId);
    }

    @Override
    public boolean existCourse(String id) throws SQLException, ClassNotFoundException {
        return courseDAO.exist(id);
    }

    @Override
    public String getNextCourseId() throws SQLException, ClassNotFoundException {
        return courseDAO.getNewId();
    }
}