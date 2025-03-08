package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentRegistrationDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;

import java.sql.SQLException;
import java.util.List;

public interface StudentRegistrationFormBO extends SuperBO {
    String getNextStudentId() throws SQLException, ClassNotFoundException;
    boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    String getStudentId(String studentName) throws SQLException;
    boolean saveStudentRegistration(StudentRegistrationDTO studentRegistrationDTO) throws SQLException, ClassNotFoundException;
    String getNextStudentRegistrationId() throws SQLException, ClassNotFoundException;
    boolean existStudentRegistration(String id) throws SQLException, ClassNotFoundException;
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    List<String> getActiveCourses() throws SQLException;
    String getCourseId(String courseName) throws SQLException;
    double getCourseFee(String courseName) throws SQLException;
    String getCourseNameByStudentId(String studentId) throws SQLException;
    List<StudentRegistrationTM> getAllStudentRegistrations() throws SQLException;
    void addRegistration(String registrationId, StudentDTO studentDTO, StudentRegistrationDTO studentRegistrationDTO, PaymentDTO paymentDTO) throws ClassNotFoundException ;






}
