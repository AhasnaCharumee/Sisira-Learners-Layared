package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.InstructorDAO;
import lk.ijse.gdse72.sisiralearners.entity.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAOImpl implements InstructorDAO {

    public ArrayList<Instructor> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Instructor");
        ArrayList<Instructor> instructors = new ArrayList<>();

        while (rst.next()) {
            Instructor instructor = new Instructor(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            instructors.add(instructor);
        }

        return instructors;
    }

    public boolean save(Instructor instructor) throws SQLException {
        return SQLUtil.execute("INSERT INTO Instructor VALUES (?,?,?,?,?)",
                instructor.getInstructor_id(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getContact(),
                instructor.getVehicle_class()
        );
    }

    public boolean update(Instructor instructor) throws SQLException {
        return SQLUtil.execute("UPDATE Instructor SET name=?, email=?, contact=?, vehicle_class=? WHERE instructor_id=?",
                instructor.getName(),
                instructor.getEmail(),
                instructor.getContact(),
                instructor.getVehicle_class(),
                instructor.getInstructor_id()
        );
    }

    public boolean delete(String instructorId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Instructor WHERE instructor_id=?", instructorId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT instructor_id FROM Instructor WHERE instructor_id=?", id);
        return resultSet.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT instructor_id FROM Instructor ORDER BY instructor_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public List<String> getAllInstructorNames() throws SQLException {
        List<String> instructorNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM Instructor");
        while (resultSet.next()) {
            instructorNames.add(resultSet.getString("name"));
        }
        return instructorNames;
    }
}