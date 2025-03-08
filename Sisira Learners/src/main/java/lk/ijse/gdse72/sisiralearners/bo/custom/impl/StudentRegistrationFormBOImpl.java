package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse72.sisiralearners.bo.custom.StudentRegistrationFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.*;
import lk.ijse.gdse72.sisiralearners.db.DBConnection;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentRegistrationDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;
import lk.ijse.gdse72.sisiralearners.entity.Payment;
import lk.ijse.gdse72.sisiralearners.entity.Student;
import lk.ijse.gdse72.sisiralearners.entity.StudentRegistration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentRegistrationFormBOImpl implements StudentRegistrationFormBO {

    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    private final StudentRegistrationDAO studentRegistrationDAO = (StudentRegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT_REGISTRATION);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);
    public QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public String getNextStudentId() throws SQLException, ClassNotFoundException {
        return studentDAO.getNewId();
    }

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        Student student = new Student(
                studentDTO.getStudent_id(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getNic(),
                studentDTO.getContact(),
                studentDTO.getPay_balance()
        );
        return studentDAO.save(student);
    }

    @Override
    public String getStudentId(String studentName) throws SQLException {
        return studentDAO.getStudentId(studentName);
    }

    @Override
    public boolean saveStudentRegistration(StudentRegistrationDTO studentRegistrationDTO) throws SQLException, ClassNotFoundException {
        StudentRegistration studentRegistration = new StudentRegistration(
                studentRegistrationDTO.getRegistration_id(),
                studentRegistrationDTO.getStudent_id(),
                studentRegistrationDTO.getCourse_id(),
                studentRegistrationDTO.getRegistration_date(),
                studentRegistrationDTO.getStatus()
        );
        return studentRegistrationDAO.save(studentRegistration);
    }

    @Override
    public String getNextStudentRegistrationId() throws SQLException, ClassNotFoundException {
        return studentRegistrationDAO.getNewId();
    }

    @Override
    public boolean existStudentRegistration(String id) throws SQLException, ClassNotFoundException {
        return studentRegistrationDAO.exist(id);
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNewId();
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(
                paymentDTO.getPayment_id(),
                paymentDTO.getStudent_id(),
                paymentDTO.getNote(),
                paymentDTO.getAmount(),
                paymentDTO.getPayment_date()
        );
        return paymentDAO.save(payment);
    }

    @Override
    public List<String> getActiveCourses() throws SQLException {
        return courseDAO.getActiveCourses();
    }

    @Override
    public String getCourseId(String courseName) throws SQLException {
        return courseDAO.getCourseId(courseName);
    }

    @Override
    public double getCourseFee(String courseName) throws SQLException {
        return courseDAO.getCourseFee(courseName);
    }

    @Override
    public String getCourseNameByStudentId(String studentId) throws SQLException {
        return courseDAO.getCourseNameByStudentId(studentId);
    }

    @Override
    public List<StudentRegistrationTM> getAllStudentRegistrations() throws SQLException {
        return queryDAO.getAllStudentRegistrations();
    }

    @Override
    public void addRegistration(String registrationId, StudentDTO studentDTO, StudentRegistrationDTO studentRegistrationDTO, PaymentDTO paymentDTO) throws ClassNotFoundException {
        Connection connection = null;
        try {
            if (existStudentRegistration(registrationId)) {
                new Alert(Alert.AlertType.WARNING, " Already exists!").show();
                return;
            }
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isStudentInserted = saveStudent(studentDTO);
            if (!isStudentInserted) throw new SQLException("Failed to insert into Student");

            boolean isStudentRegistrationInserted = saveStudentRegistration(studentRegistrationDTO);
            if (!isStudentRegistrationInserted) throw new SQLException("Failed to insert into StudentRegistration");

            boolean isPaymentInserted = savePayment(paymentDTO);
            if (!isPaymentInserted) throw new SQLException("Failed to insert into Payment");

            connection.commit();
            connection.setAutoCommit(true);
            new Alert(Alert.AlertType.INFORMATION, "Student registered successfully!").show();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to register student: " + e.getMessage()).show();
        }
    }
}