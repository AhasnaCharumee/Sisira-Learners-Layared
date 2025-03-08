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
import lk.ijse.gdse72.sisiralearners.bo.BOFactory;
import lk.ijse.gdse72.sisiralearners.bo.custom.StudentRegistrationFormBO;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentDTO;
import lk.ijse.gdse72.sisiralearners.dto.StudentRegistrationDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentRegistrationFormController implements Initializable {

    private final StudentRegistrationFormBO studentRegistrationFormBO = (StudentRegistrationFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.REGISTRATION);

    @FXML

    private Button btnRegister;


    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private Label lblCourseFee;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtInitialPayment;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtStudentRegId;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colCourseName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colReg;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableView<StudentRegistrationTM> tblStudentRegistration;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtStudentRegId.setText(studentRegistrationFormBO.getNextStudentRegistrationId());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load next student registration ID: " + e.getMessage()).show();
        }
        refreshPage();
        loadActiveCourses();
        loadStatus();
        cmbStatus.getSelectionModel().select("Active");
        cmbCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setCourseFee(newValue);
            }
        });
        initializeTableColumns();
        loadStudentRegistrations();

    }

    private void initializeTableColumns() {
        colReg.setCellValueFactory(new PropertyValueFactory<>("registration_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("student_email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("student_contact"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("pay_balance"));


    }

    private void loadStudentRegistrations() {
        try {
            List<StudentRegistrationTM> studentRegistrations = studentRegistrationFormBO.getAllStudentRegistrations();
            ObservableList<StudentRegistrationTM> observableList = FXCollections.observableArrayList(studentRegistrations);
            tblStudentRegistration.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load student registrations: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String studentId = studentRegistrationFormBO.getNextStudentId();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String nic = txtNIC.getText();
        String contact = txtPhone.getText();
        double initialPayment = Double.parseDouble(txtInitialPayment.getText());
        String courseName = cmbCourse.getSelectionModel().getSelectedItem();
        String courseId = studentRegistrationFormBO.getCourseId(courseName);
        Date registrationDate = java.sql.Date.valueOf(LocalDate.now());
        String registrationId = studentRegistrationFormBO.getNextStudentRegistrationId();
        String paymentId = studentRegistrationFormBO.getNextPaymentId();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        double courseFee = lblCourseFee.getText().isEmpty() ? 0.0 : Double.parseDouble(lblCourseFee.getText());
        double pay_balance = courseFee - initialPayment;

        StudentDTO studentDTO = new StudentDTO(studentId, name, email, nic, contact, pay_balance);
        StudentRegistrationDTO studentRegistrationDTO = new StudentRegistrationDTO(registrationId, studentId, courseId, registrationDate, status);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, "Initial Payment", initialPayment, registrationDate);

        studentRegistrationFormBO.addRegistration(registrationId, studentDTO, studentRegistrationDTO, paymentDTO);
        loadStudentRegistrations();

    }

    @FXML
    void btnResetOnAction(MouseEvent event) {
        refreshPage();
    }
    public void refreshPage(){
        try {
            txtStudentRegId.setText(studentRegistrationFormBO.getNextStudentRegistrationId());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load next student registration ID: " + e.getMessage()).show();
        }
        txtName.clear();
        txtEmail.clear();
        txtNIC.clear();
        txtPhone.clear();
        txtInitialPayment.clear();
        cmbCourse.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().select("Active");
        lblCourseFee.setText("00 000 .00");
        btnRegister.setDisable(false);
    }

    private void loadActiveCourses() {
        try {
            List<String> courses = studentRegistrationFormBO.getActiveCourses();
            cmbCourse.getItems().addAll(courses);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
        }
    }

    private void setCourseFee(String courseName) {
        try {
            double courseFee = studentRegistrationFormBO.getCourseFee(courseName);
            lblCourseFee.setText(String.format("%.2f", courseFee));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load course fee: " + e.getMessage()).show();
        }
    }

    private void loadStatus() {
        ObservableList<String> status = FXCollections.observableArrayList("Inactive", "Active");
        cmbStatus.setItems(status);
    }

    @FXML
    public void tblSROnClicked(MouseEvent mouseEvent) throws SQLException {
        StudentRegistrationTM selectedRegistration = tblStudentRegistration.getSelectionModel().getSelectedItem();
        if (selectedRegistration != null) {
            String studentId = studentRegistrationFormBO.getStudentId(selectedRegistration.getStudent_name());
            String courseName = studentRegistrationFormBO.getCourseNameByStudentId(studentId);

            txtStudentRegId.setText(selectedRegistration.getRegistration_id());
            txtName.setText(selectedRegistration.getStudent_name());
            txtEmail.setText(selectedRegistration.getStudent_email());
            txtNIC.setText(selectedRegistration.getStudent_contact());
            txtPhone.setText(selectedRegistration.getStudent_contact());
            cmbCourse.getSelectionModel().select(courseName);
            cmbStatus.getSelectionModel().select(selectedRegistration.getStatus());

            txtInitialPayment.setDisable(true);
            btnRegister.setDisable(true);
        }
    }

//    @FXML
//    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
//        String registrationId = txtStudentRegId.getText();
//        String studentId = studentModel.getStudentId(txtName.getText());
//        String name = txtName.getText();
//        String email = txtEmail.getText();
//        String nic = txtNIC.getText();
//        String contact = txtPhone.getText();
//        String courseName = cmbCourse.getSelectionModel().getSelectedItem();
//        String courseId = courseModel.getCourseId(courseName);
//        String status = cmbStatus.getSelectionModel().getSelectedItem();
//
//        StudentDto studentDto = new StudentDto(studentId, name, email, nic, contact, 0.0);
//        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto(registrationId, studentId, courseId, null, status);
//
//        Connection connection = null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            boolean isStudentUpdated = studentModel.updateStudent(studentDto);
//            if (!isStudentUpdated) throw new SQLException("Failed to update Student");
//
//            boolean isStudentRegistrationUpdated = studentRegistrationModel.updateStudentRegistration(studentRegistrationDto);
//            if (!isStudentRegistrationUpdated) throw new SQLException("Failed to update StudentRegistration");
//
//            connection.commit();
//            connection.setAutoCommit(true);
//            new Alert(Alert.AlertType.INFORMATION, "Student updated successfully!").show();
//        } catch (SQLException e) {
//            try {
//                if (connection != null) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            new Alert(Alert.AlertType.ERROR, "Failed to update student: " + e.getMessage()).show();
//        }
//    }

//    @FXML
//    public void btnDeleteOnAction(ActionEvent actionEvent) {
//        String registrationId = txtStudentRegId.getText();
//
//        Connection connection = null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            boolean isPaymentDeleted = paymentModel.deletePaymentByRegistrationId(registrationId);
//            if (!isPaymentDeleted) {
//                connection.rollback();
//                throw new SQLException("Failed to delete from Payment");
//            }
//
//            boolean isStudentDeleted = studentModel.deleteStudent(studentModel.getStudentId(txtName.getText()));
//            if (!isStudentDeleted) {
//                connection.rollback();
//                throw new SQLException("Failed to delete from Student");
//            }
//
//            boolean isStudentRegistrationDeleted = studentRegistrationModel.deleteStudentRegistration(registrationId);
//            if (!isStudentRegistrationDeleted) {
//                connection.rollback();
//                throw new SQLException("Failed to delete from StudentRegistration");
//            }
//
//            connection.commit();
//            new Alert(Alert.AlertType.INFORMATION, "Student registration deleted successfully!").show();
//            btnResetOnAction(null); // Reset the form after deletion
//        } catch (SQLException e) {
//            try {
//                if (connection != null) {
//                    connection.rollback();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            new Alert(Alert.AlertType.ERROR, "Failed to delete student registration: " + e.getMessage()).show();
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.setAutoCommit(true);
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}