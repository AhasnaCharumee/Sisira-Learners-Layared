package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.Instructor;

import java.sql.SQLException;
import java.util.List;

public interface InstructorDAO extends CrudDAO<Instructor> {
    List<String> getAllInstructorNames() throws SQLException;
}
