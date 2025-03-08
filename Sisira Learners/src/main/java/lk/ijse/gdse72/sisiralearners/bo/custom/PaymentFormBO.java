package lk.ijse.gdse72.sisiralearners.bo.custom;

import lk.ijse.gdse72.sisiralearners.bo.SuperBO;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentFormBO extends SuperBO {
    ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException;
    boolean existPayment(String id) throws SQLException, ClassNotFoundException;
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
    double getPreviousAmount(String paymentId) throws SQLException;
    boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;
    List<String> getAllStudentNames() throws SQLException;
    void addPayment(PaymentDTO paymentDTO, String studentName, double amount, double previousAmount);

    void editPayment(PaymentDTO paymentDTO, String studentName, double amount);
}
