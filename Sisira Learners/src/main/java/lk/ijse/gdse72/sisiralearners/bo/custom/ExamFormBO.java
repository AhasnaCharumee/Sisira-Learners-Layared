package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.ExamDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ExamFormBO extends SuperBO {
    ArrayList<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException;
    boolean saveExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException;
    boolean updateExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException;
    boolean deleteExam(String examId) throws SQLException, ClassNotFoundException;
    boolean existExam(String id) throws SQLException, ClassNotFoundException;
    String getNextExamId() throws SQLException, ClassNotFoundException;
    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;
    List<String> getAllStudentNames() throws SQLException;

}
