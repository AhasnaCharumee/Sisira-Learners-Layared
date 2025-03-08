package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    List<String> getAllStudentNames() throws SQLException;
    String getStudentId(String studentName) throws SQLException;
}
