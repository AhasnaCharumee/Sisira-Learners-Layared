package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.QueryDAO;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    public List<StudentRegistrationTM> getAllStudentRegistrations() throws SQLException {
        List<StudentRegistrationTM> studentRegistrations = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("""
                SELECT sr.registration_id, sr.status, s.name , c.name, s.email, s.contact, s.pay_balance
                    FROM StudentRegistration sr
                    JOIN Student s ON sr.student_id = s.student_id
                    JOIN Course c ON sr.course_id = c.course_id"""
        );

        while (resultSet.next()) {
            studentRegistrations.add(new StudentRegistrationTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getDouble(7)
            ));
        }
        return studentRegistrations;
    }
}
