package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.ExamDAO;
import lk.ijse.gdse72.sisiralearners.entity.Exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDAOImpl implements ExamDAO {

    public ArrayList<Exam> getAllData() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Exam");
        ArrayList<Exam> exams = new ArrayList<>();

        while (rst.next()) {
            Exam exam = new Exam(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
            exams.add(exam);
        }

        return exams;
    }

    public boolean save(Exam exam) throws SQLException {
        return SQLUtil.execute("INSERT INTO Exam VALUES (?,?,?,?,?)",
                exam.getExam_id(),
                exam.getExam_name(),
                exam.getStudent_id(),
                exam.getExam_date(),
                exam.getResult()
        );
    }

    public boolean update(Exam exam) throws SQLException {
        return SQLUtil.execute("UPDATE Exam SET exam_name=?, student_id=?, exam_date=?, result=? WHERE exam_id=?",
                exam.getExam_name(),
                exam.getStudent_id(),
                exam.getExam_date(),
                exam.getResult(),
                exam.getExam_id()
        );
    }

    public boolean delete(String examId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Exam WHERE exam_id=?", examId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT exam_id FROM Exam WHERE exam_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT exam_id FROM Exam ORDER BY exam_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

}