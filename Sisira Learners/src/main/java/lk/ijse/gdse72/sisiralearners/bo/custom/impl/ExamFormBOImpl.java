package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import lk.ijse.gdse72.sisiralearners.bo.custom.ExamFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.ExamDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.StudentDAO;
import lk.ijse.gdse72.sisiralearners.dto.ExamDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.entity.Exam;
import lk.ijse.gdse72.sisiralearners.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamFormBOImpl implements ExamFormBO {

    private ExamDAO examDAO = (ExamDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXAM);
    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public ArrayList<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException {
        ArrayList<Exam> exams = examDAO.getAllData();
        ArrayList<ExamDTO> examDTOs = new ArrayList<>();

        for (Exam exam : exams) {
            examDTOs.add(new ExamDTO(
                    exam.getExam_id(),
                    exam.getExam_name(),
                    exam.getStudent_id(),
                    exam.getExam_date(),
                    exam.getResult()
            ));
        }
        return examDTOs;
    }

    @Override
    public boolean saveExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam(
                examDTO.getExam_id(),
                examDTO.getExam_name(),
                examDTO.getStudent_id(),
                examDTO.getExam_date(),
                examDTO.getResult()
        );
        return examDAO.save(exam);
    }

    @Override
    public boolean updateExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam(
                examDTO.getExam_id(),
                examDTO.getExam_name(),
                examDTO.getStudent_id(),
                examDTO.getExam_date(),
                examDTO.getResult()
        );
        return examDAO.update(exam);
    }

    @Override
    public boolean deleteExam(String examId) throws SQLException, ClassNotFoundException {
        return examDAO.delete(examId);
    }

    @Override
    public boolean existExam(String id) throws SQLException, ClassNotFoundException {
        return examDAO.exist(id);
    }

    @Override
    public String getNextExamId() throws SQLException, ClassNotFoundException {
        return examDAO.getNewId();
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = studentDAO.getAllData();
        ArrayList<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : students) {
            studentDTOs.add(new StudentDTO(
                    student.getStudent_id(),
                    student.getName(),
                    student.getContact(),
                    student.getNic(),
                    student.getContact(),
                    student.getPay_balance()
            ));
        }
        return studentDTOs;
    }

    @Override
    public List<String> getAllStudentNames() throws SQLException {
        return studentDAO.getAllStudentNames();
    }
}