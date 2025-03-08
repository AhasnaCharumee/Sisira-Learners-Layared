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
import lk.ijse.gdse72.sisiralearners.bo.custom.VehicleFormBO;
import lk.ijse.gdse72.sisiralearners.dao.custom.VehicleDAO;
import lk.ijse.gdse72.sisiralearners.dto.VehicleDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.VehicleTM;
import lk.ijse.gdse72.sisiralearners.dao.custom.impl.VehicleDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleFormController implements Initializable {

    private VehicleFormBO vehicleFormBO = (VehicleFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.VEHICLE);
    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXComboBox<String> cmbVehicleClass;

    @FXML
    private AnchorPane paneVehicle;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleID;

    @FXML
    private TableColumn<VehicleTM, String> colName;

    @FXML
    private TableColumn<VehicleTM, String> colVNumber;

    @FXML
    private TableColumn<VehicleTM, String> colEngineNumber;

    @FXML
    private TableColumn<VehicleTM, String> colClass;

    @FXML
    private TableColumn<VehicleTM, String> colStatus;

    @FXML
    private TableView<VehicleTM> tblVehicle;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtVehicleNumber;

    @FXML
    private JFXTextField txtEngineNumber;

    @FXML
    void tblVehicleOnClick(MouseEvent event) {
        VehicleTM selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtID.setText(selectedItem.getVehicle_id());
            txtName.setText(selectedItem.getVehicle_name());
            txtVehicleNumber.setText(selectedItem.getVehicle_number());
            txtEngineNumber.setText(selectedItem.getEngine_number());
            cmbVehicleClass.getSelectionModel().select(selectedItem.getVehicle_class());
            cmbStatus.getSelectionModel().select(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String vehicleId = txtID.getText();
        String name = txtName.getText();
        String vehicleNumber = txtVehicleNumber.getText();
        String engineNumber = txtEngineNumber.getText();
        String vehicleClass = cmbVehicleClass.getValue();
        String status = cmbStatus.getValue();

        VehicleDTO vehicleDto = new VehicleDTO(vehicleId, name, vehicleNumber, engineNumber, vehicleClass, status);

        try {
            if (vehicleFormBO.existVehicle(vehicleId)) {
                new Alert(Alert.AlertType.WARNING, " Vehicle Id already exists!").show();
                return;
            }
            boolean isSaved = vehicleFormBO.saveVehicle(vehicleDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save vehicle!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the vehicle: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String vehicleId = txtID.getText();
        String name = txtName.getText();
        String vehicleNumber = txtVehicleNumber.getText();
        String engineNumber = txtEngineNumber.getText();
        String vehicleClass = cmbVehicleClass.getValue();
        String status = cmbStatus.getValue();

        VehicleDTO vehicleDto = new VehicleDTO(vehicleId, name, vehicleNumber, engineNumber, vehicleClass, status);
        boolean isUpdated = vehicleFormBO.updateVehicle(vehicleDto);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Vehicle updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update vehicle!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String vehicleId = txtID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this vehicle?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = vehicleFormBO.deleteVehicle(vehicleId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete vehicle!").show();
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
        colVehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("vehicle_name"));
        colVNumber.setCellValueFactory(new PropertyValueFactory<>("vehicle_number"));
        colEngineNumber.setCellValueFactory(new PropertyValueFactory<>("engine_number"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("vehicle_class"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            refreshPage();
            loadStatus();
            loadVehicleClass();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextVehicleId = vehicleFormBO.getNextVehicleId();
        txtID.setText(nextVehicleId);

        txtName.setText("");
        txtVehicleNumber.setText("");
        txtEngineNumber.setText("");
        cmbVehicleClass.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
        loadVehicleClass();
        loadStatus();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleFormBO.getAllVehicles();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDTO vehicleDto : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDto.getVehicle_id(),
                    vehicleDto.getVehicle_name(),
                    vehicleDto.getVehicle_number(),
                    vehicleDto.getEngine_number(),
                    vehicleDto.getVehicle_class(),
                    vehicleDto.getStatus()
            );
            vehicleTMS.add(vehicleTM);
        }
        tblVehicle.setItems(vehicleTMS);
    }

    private void loadStatus() {
        ObservableList<String> status = FXCollections.observableArrayList("Working", "Sold", "Down for Repair", "Broken");
        cmbStatus.setItems(status);
    }

    private void loadVehicleClass() {
        ObservableList<String> Vclass = FXCollections.observableArrayList("A", "B", "A1", "B", "B1", "B2", "C", "C1", "C2", "G1", "D1", "J", "H");
        cmbVehicleClass.setItems(Vclass);
    }
}