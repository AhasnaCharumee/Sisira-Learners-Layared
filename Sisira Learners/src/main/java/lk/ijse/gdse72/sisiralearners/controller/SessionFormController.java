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
import lk.ijse.gdse72.sisiralearners.bo.custom.SessionFormBO;
import lk.ijse.gdse72.sisiralearners.dto.SessionDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.SessionTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionFormController implements Initializable {

    private SessionFormBO sessionFormBO = (SessionFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbDay;

    @FXML
    private JFXComboBox<String> cmbInstructorNames;

    @FXML
    private JFXComboBox<String> cmbVehicleName;

    @FXML
    private TableColumn<SessionTM, String> colDay;

    @FXML
    private TableColumn<SessionTM, String> colEnadTime;

    @FXML
    private TableColumn<SessionTM, String> colInstructor;

    @FXML
    private TableColumn<SessionTM, String> colSessionId;

    @FXML
    private TableColumn<SessionTM, String> colStartTime;

    @FXML
    private TableColumn<SessionTM, String> colVehicle;

    @FXML
    private AnchorPane paneSession;

    @FXML
    private TableView<SessionTM> tblSession;

    @FXML
    private JFXTextField txtEndTime;

    @FXML
    private JFXTextField txtSessionId;

    @FXML
    private JFXTextField txtStartTime;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String sessionId = txtSessionId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this session?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = sessionFormBO.deleteSession(sessionId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Session deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete session!").show();
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
        String sessionId = txtSessionId.getText();
        String instructorName = cmbInstructorNames.getValue();
        String vehicleName = cmbVehicleName.getValue();
        String day = cmbDay.getValue();
        String startTime = txtStartTime.getText();
        String endTime = txtEndTime.getText();

        try {

            if (sessionFormBO.existSession(sessionId)) {
                new Alert(Alert.AlertType.WARNING, "Session ID already exists!").show();
                return;
            }
            String instructorId = sessionFormBO.getAllInstructors().stream()
                    .filter(instructor -> instructor.getName().equals(instructorName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Instructor not found"))
                    .getInstructor_id();

            String vehicleId = sessionFormBO.getAllVehicles().stream()
                    .filter(vehicle -> vehicle.getVehicle_name().equals(vehicleName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Vehicle not found"))
                    .getVehicle_id();

            SessionDTO sessionDTO = new SessionDTO(sessionId, instructorId, vehicleId, day, startTime, endTime);
            boolean isSaved = sessionFormBO.saveSession(sessionDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Session saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save session!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the session: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) throws SQLException {
        String sessionId = txtSessionId.getText();
        String instructorName = cmbInstructorNames.getValue();
        String vehicleName = cmbVehicleName.getValue();
        String day = cmbDay.getValue();
        String startTime = txtStartTime.getText();
        String endTime = txtEndTime.getText();

        try {
            String instructorId = sessionFormBO.getAllInstructors().stream()
                    .filter(instructor -> instructor.getName().equals(instructorName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Instructor not found"))
                    .getInstructor_id();

            String vehicleId = sessionFormBO.getAllVehicles().stream()
                    .filter(vehicle -> vehicle.getVehicle_name().equals(vehicleName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Vehicle not found"))
                    .getVehicle_id();

            SessionDTO sessionDTO = new SessionDTO(sessionId, instructorId, vehicleId, day, startTime, endTime);
            boolean isUpdated = sessionFormBO.updateSession(sessionDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Session updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update session!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the session: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("session_id"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
        colVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle_name"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        colEnadTime.setCellValueFactory(new PropertyValueFactory<>("end_time"));

        try {
            refreshPage();
            loadDays();
            loadInstructorNames();
            loadVehicleNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblSessionOnClick(MouseEvent event) {
        SessionTM selectedItem = tblSession.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtSessionId.setText(selectedItem.getSession_id());
            cmbInstructorNames.getSelectionModel().select(selectedItem.getInstructor_name());
            cmbVehicleName.getSelectionModel().select(selectedItem.getVehicle_name());
            cmbDay.getSelectionModel().select(selectedItem.getDay());
            txtStartTime.setText(selectedItem.getStart_time());
            txtEndTime.setText(selectedItem.getEnd_time());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextSessionId = sessionFormBO.getNextSessionId();
        txtSessionId.setText(nextSessionId);

        txtStartTime.setText("");
        txtEndTime.setText("");
        cmbDay.getSelectionModel().clearSelection();
        cmbInstructorNames.getSelectionModel().clearSelection();
        cmbVehicleName.getSelectionModel().clearSelection();
        loadDays();
        loadInstructorNames();
        loadVehicleNames();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<SessionDTO> sessionDTOS = sessionFormBO.getAllSessions();
        ObservableList<SessionTM> sessionTMS = FXCollections.observableArrayList();

        for (SessionDTO sessionDTO : sessionDTOS) {
            String instructorName = sessionFormBO.getAllInstructors().stream()
                    .filter(instructor -> instructor.getInstructor_id().equals(sessionDTO.getInstructor_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Instructor not found"))
                    .getName();

            String vehicleName = sessionFormBO.getAllVehicles().stream()
                    .filter(vehicle -> vehicle.getVehicle_id().equals(sessionDTO.getVehicle_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Vehicle not found"))
                    .getVehicle_name();

            SessionTM sessionTM = new SessionTM(
                    sessionDTO.getSession_id(),
                    instructorName,
                    vehicleName,
                    sessionDTO.getDay(),
                    sessionDTO.getStart_time(),
                    sessionDTO.getEnd_time()
            );
            sessionTMS.add(sessionTM);
        }
        tblSession.setItems(sessionTMS);
    }

    private void loadDays() {
        ObservableList<String> days = FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        cmbDay.setItems(days);
    }

    private void loadInstructorNames() throws SQLException {
        ObservableList<String> instructorNames = FXCollections.observableArrayList();
        instructorNames.addAll(sessionFormBO.getAllInstructorNames());
        cmbInstructorNames.setItems(instructorNames);
    }

    private void loadVehicleNames() throws SQLException {
        ObservableList<String> vehicleNames = FXCollections.observableArrayList();
        vehicleNames.addAll(sessionFormBO.getAllVehicleNames());
        cmbVehicleName.setItems(vehicleNames);
    }
}