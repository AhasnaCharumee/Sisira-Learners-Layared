package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.StudentDAO;
import lk.ijse.gdse72.sisiralearners.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    public ArrayList<Student> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Student");
        ArrayList<Student> students = new ArrayList<>();

        while (rst.next()) {
            Student student = new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            );
            students.add(student);
        }

        return students;
    }

    public boolean save(Student student) throws SQLException {
        return SQLUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",
                student.getStudent_id(),
                student.getName(),
                student.getEmail(),
                student.getNic(),
                student.getContact(),
                student.getPay_balance()
        );
    }

    public boolean update(Student student) throws SQLException {
        return SQLUtil.execute("UPDATE Student SET name=?, email=?, nic=?, contact=?, pay_balance=? WHERE student_id=?",
                student.getName(),
                student.getEmail(),
                student.getNic(),
                student.getContact(),
                student.getPay_balance(),
                student.getStudent_id()

        );
    }

    public boolean delete(String studentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Student WHERE student_id=?", studentId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT student_id FROM Student WHERE student_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT student_id FROM Student ORDER BY student_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }


    public List<String> getAllStudentNames() throws SQLException {
        List<String> studentNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM Student");
        while (resultSet.next()) {
            studentNames.add(resultSet.getString("name"));
        }
        return studentNames;
    }

    public String getStudentId(String studentName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT student_id FROM Student WHERE name=?", studentName);
        if (resultSet.next()) {
            return resultSet.getString("student_id");
        }
        return null;
    }
}