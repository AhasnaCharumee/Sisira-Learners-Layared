package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.StudentRegistrationDAO;
import lk.ijse.gdse72.sisiralearners.entity.StudentRegistration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentRegistrationDAOImpl implements StudentRegistrationDAO {

    @Override
    public ArrayList<StudentRegistration> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean save(StudentRegistration studentRegistration) throws SQLException {
        return SQLUtil.execute("INSERT INTO StudentRegistration (registration_id, student_id, course_id, registration_date, status) VALUES (?, ?, ?, ?,?)",
                studentRegistration.getRegistration_id(),
                studentRegistration.getStudent_id(),
                studentRegistration.getCourse_id(),
                studentRegistration.getRegistration_date(),
                studentRegistration.getStatus()
        );
    }

    @Override
    public boolean update(StudentRegistration dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }


    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT registration_id FROM StudentRegistration ORDER BY registration_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SR%03d", newIdIndex);
        }
        return "SR001";
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT registration_id FROM StudentRegistration WHERE registration_id=?", id);
        return rst.next();
    }

}