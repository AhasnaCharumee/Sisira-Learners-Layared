package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse72.sisiralearners.bo.BOFactory;
import lk.ijse.gdse72.sisiralearners.bo.custom.LoginFormBO;
import lk.ijse.gdse72.sisiralearners.bo.custom.UserFormBO;
import lk.ijse.gdse72.sisiralearners.bo.custom.impl.LoginFormBOImpl;
import lk.ijse.gdse72.sisiralearners.bo.custom.impl.UserFormBOImpl;
import lk.ijse.gdse72.sisiralearners.dao.custom.UserDAO;
import lk.ijse.gdse72.sisiralearners.dao.custom.impl.UserDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {


    @FXML
    private AnchorPane loginpane;

    @FXML
    private Button signInbtn;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    private LoginFormBO loginFormBO = (LoginFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void signInbtnOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            boolean authenticate = loginFormBO.authenticateUser(userName, password);
            if (authenticate) {
                // Hide the login window
                Window window = loginpane.getScene().getWindow();
                window.hide();

                // Load the dashboard
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Sisira Learners");
                stage.setFullScreen(true);
                stage.setScene(new Scene(anchorPane));
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid user name or password!").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }
}