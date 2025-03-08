package lk.ijse.gdse72.sisiralearners.dao.custom;

import lk.ijse.gdse72.sisiralearners.dao.CrudDAO;
import lk.ijse.gdse72.sisiralearners.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    double getPreviousAmount(String paymentId) throws SQLException;
    List<String> getAllStudentNames() throws SQLException;
}
