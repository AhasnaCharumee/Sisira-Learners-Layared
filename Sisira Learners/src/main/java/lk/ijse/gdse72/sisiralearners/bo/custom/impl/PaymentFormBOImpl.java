package lk.ijse.gdse72.sisiralearners.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse72.sisiralearners.bo.custom.PaymentFormBO;
import lk.ijse.gdse72.sisiralearners.dao.DAOFactory;
import lk.ijse.gdse72.sisiralearners.dao.custom.PaymentDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.StudentDAO;
import lk.ijse.gdse72.sisiralearners.db.DBConnection;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.entity.Payment;
import lk.ijse.gdse72.sisiralearners.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormBOImpl implements PaymentFormBO {

    private PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = paymentDAO.getAllData();
        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOs.add(new PaymentDTO(
                    payment.getPayment_id(),
                    payment.getStudent_id(),
                    payment.getNote(),
                    payment.getAmount(),
                    payment.getPayment_date()
            ));
        }
        return paymentDTOs;
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
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(
                paymentDTO.getPayment_id(),
                paymentDTO.getStudent_id(),
                paymentDTO.getNote(),
                paymentDTO.getAmount(),
                paymentDTO.getPayment_date()
        );
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public boolean existPayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.exist(id);
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNewId();
    }

    @Override
    public double getPreviousAmount(String paymentId) throws SQLException {
        return paymentDAO.getPreviousAmount(paymentId);
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        Student student = new Student(
                studentDTO.getStudent_id(),
                studentDTO.getName(),
                studentDTO.getContact(),
                studentDTO.getNic(),
                studentDTO.getContact(),
                studentDTO.getPay_balance()
        );
        return studentDAO.update(student);
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
        return paymentDAO.getAllStudentNames();
    }

    @Override
    public void addPayment(PaymentDTO paymentDTO, String studentName, double amount, double previousAmount) {
        Connection connection = null;
        try {
            if (existPayment(paymentDTO.getPayment_id())) {
                new Alert(Alert.AlertType.WARNING, "Payment ID already exists!").show();
                return;
            }
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Retrieve the student details
            StudentDTO studentDTO = getAllStudents().stream()
                    .filter(s -> s.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"));

            boolean isSaved = savePayment(paymentDTO);

            if (isSaved) {
                // Update the student's pay_balance
                double newPayBalance;
                if (previousAmount < amount) {
                    newPayBalance = studentDTO.getPay_balance() - previousAmount + amount;
                } else {
                    newPayBalance = studentDTO.getPay_balance() - previousAmount - amount;
                }
                studentDTO.setPay_balance(newPayBalance);
                boolean isUpdated = updateStudent(studentDTO);

                if (isUpdated) {
                    connection.commit();
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved and student balance updated successfully!").show();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Failed to update student balance!").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the payment: " + e.getMessage()).show();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void editPayment(PaymentDTO paymentDTO, String studentName, double amount) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Retrieve the student details
            StudentDTO studentDTO = getAllStudents().stream()
                    .filter(s -> s.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"));

            boolean isUpdated = updatePayment(paymentDTO);

            if (isUpdated) {
                // Update the student's pay_balance
                double newPayBalance = studentDTO.getPay_balance() - amount;
                studentDTO.setPay_balance(newPayBalance);
                boolean isStudentUpdated = updateStudent(studentDTO);

                if (isStudentUpdated) {
                    connection.commit();
                    new Alert(Alert.AlertType.INFORMATION, "Payment updated and student balance updated successfully!").show();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Failed to update student balance!").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to update payment!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the payment: " + e.getMessage()).show();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}