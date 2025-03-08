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
import lk.ijse.gdse72.sisiralearners.bo.custom.BookingFormBO;
import lk.ijse.gdse72.sisiralearners.dto.BookingDTO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.BookingTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingFormController implements Initializable {

    BookingFormBO bookingFormBO  = (BookingFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOKING);

    private Map<String, String> sessionMap = new HashMap<>();

    @FXML
    private DatePicker DPbookingDate;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbPractiseSession;

    @FXML
    private JFXComboBox<String> cmbStudent;

    @FXML
    private TableColumn<BookingTM, String> colBookingDate;

    @FXML
    private TableColumn<BookingTM, String> colBookingId;

    @FXML
    private TableColumn<BookingTM, String> colPractiseSession;

    @FXML
    private TableColumn<BookingTM, String> colStudentName;

    @FXML
    private AnchorPane paneSession;

    @FXML
    private TableView<BookingTM> tblBooking;

    @FXML
    private JFXTextField txtBookingId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookingId = txtBookingId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this booking?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = bookingFormBO.deleteBooking(bookingId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Booking deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete booking!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String bookingId = txtBookingId.getText();
        String studentName = cmbStudent.getValue();
        String practiseSession = cmbPractiseSession.getValue();
        String sessionId = getSelectedSessionId();
        java.sql.Date bookingDate = java.sql.Date.valueOf(DPbookingDate.getValue());

        try {
                if (bookingFormBO.existBooking(bookingId)) {
                    new Alert(Alert.AlertType.WARNING, "Booking ID already exists!").show();
                    return;
                }
            String studentId = bookingFormBO.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            BookingDTO bookingDto = new BookingDTO(bookingId, studentId, sessionId, bookingDate, practiseSession);
            boolean isSaved = bookingFormBO.saveBooking(bookingDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Booking saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save booking!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the booking: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String bookingId = txtBookingId.getText();
        String studentName = cmbStudent.getValue();
        String practiseSession = cmbPractiseSession.getValue();
        String sessionId = getSelectedSessionId();
        java.sql.Date bookingDate = java.sql.Date.valueOf(DPbookingDate.getValue());

        try {
            String studentId = bookingFormBO.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            BookingDTO bookingDto = new BookingDTO(bookingId, studentId, sessionId, bookingDate, practiseSession);
            boolean isUpdated = bookingFormBO.updateBooking(bookingDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Booking updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update booking!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the booking: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblBookingOnClick(MouseEvent event) {
        BookingTM selectedItem = tblBooking.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtBookingId.setText(selectedItem.getBooking_id());
            cmbStudent.getSelectionModel().select(selectedItem.getStudent_name());
            cmbPractiseSession.getSelectionModel().select(selectedItem.getPractise_session());

            java.util.Date paymentDate = selectedItem.getBooking_date();
            if (paymentDate != null) {
                DPbookingDate.setValue(new java.sql.Date(paymentDate.getTime()).toLocalDate());
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colPractiseSession.setCellValueFactory(new PropertyValueFactory<>("practise_session"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("booking_date"));

        try {
            refreshPage();
            loadStudents();
            loadPractiseSessions();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextBookingId = bookingFormBO.getNextBookingId();
        txtBookingId.setText(nextBookingId);

        DPbookingDate.setValue(null);
        cmbStudent.getSelectionModel().clearSelection();
        cmbPractiseSession.getSelectionModel().clearSelection();
        loadStudents();
        loadPractiseSessions();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<BookingDTO> bookingDTOS = bookingFormBO.getAllBookings();
        ObservableList<BookingTM> bookingTMS = FXCollections.observableArrayList();

        for (BookingDTO bookingDto : bookingDTOS) {
            String studentName = bookingFormBO.getAllStudents().stream()
                    .filter(student -> student.getStudent_id().equals(bookingDto.getStudent_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getName();

            BookingTM bookingTM = new BookingTM(
                    bookingDto.getBooking_id(),
                    studentName,
                    bookingDto.getPractise_session(),
                    bookingDto.getBooking_date()
            );
            bookingTMS.add(bookingTM);
        }
        tblBooking.setItems(bookingTMS);
    }

    private void loadStudents() throws SQLException {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        studentNames.addAll(bookingFormBO.getAllStudentNames());
        cmbStudent.setItems(studentNames);
    }

    private void loadPractiseSessions() throws SQLException, ClassNotFoundException {
        ObservableList<String> practiseSessions = FXCollections.observableArrayList();
        ArrayList<SessionDTO> sessionDTOS = bookingFormBO.getAllSessions();

        for (SessionDTO sessionDTO : sessionDTOS) {
            String formattedSession = String.format("%s %s - %s", sessionDTO.getDay(), sessionDTO.getStart_time(), sessionDTO.getEnd_time());
            practiseSessions.add(formattedSession);
            sessionMap.put(formattedSession, sessionDTO.getSession_id());
        }

        cmbPractiseSession.setItems(practiseSessions);
    }

    private String getSelectedSessionId() {
        String selectedSession = cmbPractiseSession.getValue();
        return sessionMap.get(selectedSession);
    }
}