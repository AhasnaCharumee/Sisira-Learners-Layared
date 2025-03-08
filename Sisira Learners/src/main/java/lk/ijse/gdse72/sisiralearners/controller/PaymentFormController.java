package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.sisiralearners.bo.BOFactory;
import lk.ijse.gdse72.sisiralearners.bo.custom.PaymentFormBO;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.PaymentTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    private PaymentFormBO paymentFormBO = (PaymentFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);


    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbStudentNames;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, java.sql.Date> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colStudentName;

    @FXML
    private TableColumn<?, ?> colNote;

    @FXML
    private AnchorPane paneExam;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtNote;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = paymentFormBO.deletePayment(paymentId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete payment!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = txtPaymentId.getText();
        String studentName = cmbStudentNames.getValue();
        String note = txtNote.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        java.sql.Date paymentDate = new java.sql.Date(System.currentTimeMillis());
        double previousAmount = paymentFormBO.getPreviousAmount(paymentId);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentName, note, amount, paymentDate);
        paymentFormBO.addPayment(paymentDTO, studentName, amount , previousAmount);
        refreshPage();
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = txtPaymentId.getText();
        String studentName = cmbStudentNames.getValue();
        String note = txtNote.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        java.sql.Date paymentDate = new java.sql.Date(System.currentTimeMillis());

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentName, note, amount, paymentDate);

        paymentFormBO.editPayment(paymentDTO, studentName, amount);
        refreshPage();
    }


    @FXML
    void tblPaymentOnClicked(MouseEvent event) {
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtPaymentId.setText(selectedItem.getPayment_id());
            cmbStudentNames.getSelectionModel().select(selectedItem.getStudent_id());
            txtNote.setText(selectedItem.getNote());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));

            // Convert java.util.Date to java.time.LocalDate
            java.util.Date paymentDate = selectedItem.getPayment_date();
            if (paymentDate != null) {
                datepicker.setValue(new java.sql.Date(paymentDate.getTime()).toLocalDate());
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));

        datepicker.setValue(LocalDate.now());

        try {
            refreshPage();
            loadStudentNames();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextPaymentId = paymentFormBO.getNextPaymentId();
        txtPaymentId.setText(nextPaymentId);
        txtNote.setText("");
        txtAmount.setText("");
        cmbStudentNames.getSelectionModel().clearSelection();
        loadStudentNames();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> paymentDTOS = paymentFormBO.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            String studentName = paymentFormBO.getAllStudents().stream()
                    .filter(student -> student.getStudent_id().equals(paymentDTO.getStudent_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getName();

            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPayment_id(),
                    studentName,
                    paymentDTO.getNote(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPayment_date()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayment.setItems(paymentTMS);
    }

    private void loadStudentNames() throws SQLException {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        studentNames.addAll(paymentFormBO.getAllStudentNames());
        cmbStudentNames.setItems(studentNames);
    }
}