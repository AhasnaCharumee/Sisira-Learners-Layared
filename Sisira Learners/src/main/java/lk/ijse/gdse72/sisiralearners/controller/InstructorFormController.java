
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
import lk.ijse.gdse72.sisiralearners.bo.custom.InstructorFormBO;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.InstructorTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorFormController implements Initializable {

    private InstructorFormBO instructorFormBO = (InstructorFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INSTRUCTOR);

    @FXML
    private JFXComboBox<String> cmbVehicleClass;

    @FXML
    private AnchorPane paneInstructor;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InstructorTM, String> colInstructorID;

    @FXML
    private TableColumn<InstructorTM, String> colName;

    @FXML
    private TableColumn<InstructorTM, String> colEmail;

    @FXML
    private TableColumn<InstructorTM, String> colContact;

    @FXML
    private TableColumn<InstructorTM, String> colVehicleClass;

    @FXML
    private TableView<InstructorTM> tblInstructor;

    @FXML
    private JFXTextField txtInstructorId;

    @FXML
    private JFXTextField txtInstructorName;

    @FXML
    private JFXTextField txtInstructorEmail;

    @FXML
    private JFXTextField txtContact;

    @FXML
    void tblInstructorOnClick(MouseEvent event) {
        InstructorTM selectedItem = tblInstructor.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtInstructorId.setText(selectedItem.getInstructor_id());
            txtInstructorName.setText(selectedItem.getName());
            txtInstructorEmail.setText(selectedItem.getEmail());
            txtContact.setText(selectedItem.getContact());
            cmbVehicleClass.getSelectionModel().select(selectedItem.getVehicle_class());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String instructorId = txtInstructorId.getText();
        String name = txtInstructorName.getText();
        String email = txtInstructorEmail.getText();
        String contact = txtContact.getText();
        String vehicleClass = cmbVehicleClass.getValue();

        InstructorDTO instructorDTO = new InstructorDTO(instructorId, name, email, contact, vehicleClass);

        try {
            if (instructorFormBO.existInstructor(instructorId)) {
                new Alert(Alert.AlertType.WARNING, "Instructor ID already exists!").show();
                return;
            }
            boolean isSaved = instructorFormBO.saveInstructor(instructorDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the instructor: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String instructorId = txtInstructorId.getText();
        String name = txtInstructorName.getText();
        String email = txtInstructorEmail.getText();
        String contact = txtContact.getText();
        String vehicleClass = cmbVehicleClass.getValue();

        InstructorDTO instructorDTO = new InstructorDTO(instructorId, name, email, contact, vehicleClass);
        boolean isUpdated = instructorFormBO.updateInstructor(instructorDTO);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update instructor!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String instructorId = txtInstructorId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this instructor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = instructorFormBO.deleteInstructor(instructorId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete instructor!").show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInstructorID.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colVehicleClass.setCellValueFactory(new PropertyValueFactory<>("vehicle_class"));

        try {
            refreshPage();
            loadVehicleClass();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextInstructorId = instructorFormBO.getNextInstructorId();
        txtInstructorId.setText(nextInstructorId);

        txtInstructorName.setText("");
        txtInstructorEmail.setText("");
        txtContact.setText("");
        cmbVehicleClass.getSelectionModel().clearSelection();
        loadVehicleClass();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<InstructorDTO> instructorDTOS = instructorFormBO.getAllInstructors();
        ObservableList<InstructorTM> instructorTMS = FXCollections.observableArrayList();

        for (InstructorDTO instructorDTO : instructorDTOS) {
            InstructorTM instructorTM = new InstructorTM(
                    instructorDTO.getInstructor_id(),
                    instructorDTO.getName(),
                    instructorDTO.getEmail(),
                    instructorDTO.getContact(),
                    instructorDTO.getVehicle_class()
            );
            instructorTMS.add(instructorTM);
        }
        tblInstructor.setItems(instructorTMS);
    }

    private void loadVehicleClass() {
        ObservableList<String> Vclass = FXCollections.observableArrayList("A", "B", "A1", "B", "B1", "B2", "C", "C1", "C2", "G1", "D1", "J", "H");
        cmbVehicleClass.setItems(Vclass);
    }
}
