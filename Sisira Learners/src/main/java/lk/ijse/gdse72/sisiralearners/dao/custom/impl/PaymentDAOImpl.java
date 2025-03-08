package lk.ijse.gdse72.sisiralearners.dao.custom.impl;

import lk.ijse.gdse72.sisiralearners.dao.SQLUtil;
import lk.ijse.gdse72.sisiralearners.dao.custom.PaymentDAO;
import lk.ijse.gdse72.sisiralearners.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public ArrayList<Payment> getAllData() throws SQLException {
        ArrayList<Payment> payments = new ArrayList<>();
        try (ResultSet rst = SQLUtil.execute("SELECT * FROM Payment")) {
            while (rst.next()) {
                String paymentId = rst.getString(1);
                String studentId = rst.getString(2);
                String note = rst.getString(3);
                double amount = rst.getDouble(4);
                Date paymentDate = rst.getDate(5);

                Payment payment = new Payment(paymentId, studentId, note, amount, paymentDate);
                payments.add(payment);
            }
        }
        return payments;
    }

    public boolean save(Payment payment) throws SQLException {
        return SQLUtil.execute("INSERT INTO Payment (payment_id, student_id, note, amount, payment_date) VALUES (?,?,?,?,?)",
                payment.getPayment_id(),
                payment.getStudent_id(),
                payment.getNote(),
                payment.getAmount(),
                new java.sql.Date(payment.getPayment_date().getTime())
        );
    }

    public boolean update(Payment payment) throws SQLException {
        return SQLUtil.execute("UPDATE Payment SET student_id=?, note=?, amount=?, payment_date=? WHERE payment_id=?",
                payment.getStudent_id(),
                payment.getNote(),
                payment.getAmount(),
                new java.sql.Date(payment.getPayment_date().getTime()),
                payment.getPayment_id()
        );
    }

    public boolean delete(String paymentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Payment WHERE payment_id=?", paymentId);
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT payment_id FROM Payment WHERE payment_id=?", id);
        return rst.next();
    }

    public String getNewId() throws SQLException {
        try (ResultSet rst = SQLUtil.execute("SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1")) {
            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(1);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("P%03d", newIdIndex);
            }
        }
        return "P001";
    }

    @Override
    public double getPreviousAmount(String paymentId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute(
                "SELECT amount FROM Payment WHERE payment_id = ?",
                paymentId
        );
        if (resultSet.next()) {
            return resultSet.getDouble("amount");
        }
        return 0;
    }

    @Override
    public List<String> getAllStudentNames() throws SQLException {
        List<String> studentNames = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT name FROM Student");
        while (rst.next()) {
            studentNames.add(rst.getString("name"));
        }
        return studentNames;
    }

}