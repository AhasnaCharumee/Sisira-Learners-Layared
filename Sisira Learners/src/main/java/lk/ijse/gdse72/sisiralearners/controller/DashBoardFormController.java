package lk.ijse.gdse72.sisiralearners.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    @FXML
    public Label lblTime;
    @FXML
    public Label lblDate;
    @FXML
    private ImageView btnBooking;
    @FXML
    private ImageView btnCourses;
    @FXML
    private ImageView btnInstructor;
    @FXML
    private ImageView btnLogOt;
    @FXML
    private ImageView btnPayment;
    @FXML
    private ImageView btnSession;
    @FXML
    private ImageView btnStudent;
    @FXML
    private ImageView btnUsersettigs;
    @FXML
    private ImageView btnVehicle;
    @FXML
    private AnchorPane dashBoardpane;
    @FXML
    private AnchorPane homepage;
    @FXML
    private AnchorPane navpanel;
    @FXML
    private ImageView btnExam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDateTime();
    }

    private void initializeDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh : mm a");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            lblDate.setText(now.format(dateFormatter));
            lblTime.setText(now.format(timeFormatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void btnBookingOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/BookingForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnCoursesOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CourseForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnInstructorOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/InstructorForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnLogOtOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) dashBoardpane.getScene().getWindow();
        stage.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Sisira Learners");
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    @FXML
    void btnPaymentOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnSessionOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SessionForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnStudentOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentRegistrationForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnUsersettigsOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnVehicleOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void btnExamOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/ExamForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }
    @FXML
    public void btndashboardOnAction(MouseEvent mouseEvent) {
    }
}