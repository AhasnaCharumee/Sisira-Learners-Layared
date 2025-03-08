package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.sisiralearners.bo.BOFactory;
import lk.ijse.gdse72.sisiralearners.bo.custom.UserFormBO;
import lk.ijse.gdse72.sisiralearners.dto.UserDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.UserTM;

import java.sql.SQLException;
import java.util.List;

public class UserFormController {

    private UserFormBO userFormBO = (UserFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;



    @FXML
    void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        
        loadRoles();
        loadUsers();
    }

    private void loadRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");
        cmbRole.setItems(roles);
    }

    private void loadUsers() {
        try {
            List<UserDTO> userDTOS = userFormBO.getAllUsers();
            ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
            for (UserDTO userDTO : userDTOS) {
                userTMS.add(new UserTM(
                        userDTO.getUser_id(),
                        userDTO.getUser_name(),
                        userDTO.getEmail(),
                        userDTO.getPassword(),
                        userDTO.getRole()
                ));
            }
            tblUser.setItems(userTMS);
            txtUserId.setText(userFormBO.getNextUserId());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load users: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        try {
            boolean isDeleted = userFormBO.deleteUser(userId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete user: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        resetForm();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        UserDTO userDTO = new UserDTO(userId, userName, email, password, role);
        try {
            if (userFormBO.existUser(userId)) {
                new Alert(Alert.AlertType.WARNING, " Already exists!").show();
                return;
            }
            boolean isSaved = userFormBO.saveUser(userDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save user: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        UserDTO userDTO = new UserDTO(userId, userName, email, password, role);
        try {
            boolean isUpdated = userFormBO.updateUser(userDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update user: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblSessionOnClick(MouseEvent event) {
        UserTM selectedUser = tblUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtUserId.setText(selectedUser.getUser_id());
            txtUserName.setText(selectedUser.getUser_name());
            txtEmail.setText(selectedUser.getEmail());
            txtPassword.setText(selectedUser.getPassword());
            cmbRole.setValue(selectedUser.getRole());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void resetForm() throws SQLException, ClassNotFoundException {
        txtUserId.setText(userFormBO.getNextUserId());
        txtUserName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        cmbRole.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}