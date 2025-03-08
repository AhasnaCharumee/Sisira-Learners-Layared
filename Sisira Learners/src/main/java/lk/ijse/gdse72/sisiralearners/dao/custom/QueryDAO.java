package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.SuperDAO;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<StudentRegistrationTM> getAllStudentRegistrations() throws SQLException;
}
