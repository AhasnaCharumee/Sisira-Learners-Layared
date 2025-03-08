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
import lk.ijse.gdse72.sisiralearners.bo.custom.CourseFormBO;
import lk.ijse.gdse72.sisiralearners.dto.CourseDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.CourseTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseFormController implements Initializable {

    private CourseFormBO courseFormBO = (CourseFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.COURSE);

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseTM, String> colDuration;

    @FXML
    private TableColumn<CourseTM, String> colID;

    @FXML
    private TableColumn<CourseTM, String> colName;

    @FXML
    private TableColumn<CourseTM, Double> colPrice;

    @FXML
    private TableColumn<CourseTM, String> colStatus;

    @FXML
    private AnchorPane paneCourse;

    @FXML
    private TableView<CourseTM> tblCourse;

    @FXML
    private JFXTextField txtCourseId;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String courseId = txtCourseId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this course?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = courseFormBO.deleteCourse(courseId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete course!").show();
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
        String courseId = txtCourseId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        Double price = Double.valueOf(txtPrice.getText());
        String status = cmbStatus.getValue();

        CourseDTO courseDTO = new CourseDTO(courseId, status, name, duration, price);
        try {
            if (courseFormBO.existCourse(courseId)) {
                new Alert(Alert.AlertType.WARNING, "Course ID already exists!").show();
                return;
            }
            boolean isSaved = courseFormBO.saveCourse(courseDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Course saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save course!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the course: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String courseId = txtCourseId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        Double price = Double.valueOf(txtPrice.getText());
        String status = cmbStatus.getValue();

        CourseDTO courseDTO = new CourseDTO(courseId, status, name, duration, price);
        try {
            boolean isUpdated = courseFormBO.updateCourse(courseDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Course updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update course!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the course: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblCourseOnClick(MouseEvent event) {
        CourseTM selectedItem = tblCourse.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtCourseId.setText(selectedItem.getCourse_id());
            txtName.setText(selectedItem.getName());
            txtDuration.setText(selectedItem.getDuration());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            cmbStatus.setValue(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            refreshPage();
            loadStatuses();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextCourseId = courseFormBO.getNextCourseId();
        txtCourseId.setText(nextCourseId);

        txtName.setText("");
        txtDuration.setText("");
        txtPrice.setText("");
        cmbStatus.getSelectionModel().clearSelection();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<CourseDTO> cours = courseFormBO.getAllCourses();
        ObservableList<CourseTM> courseTMS = FXCollections.observableArrayList();

        for (CourseDTO courseDTO : cours) {
            CourseTM courseTM = new CourseTM(
                    courseDTO.getCourse_id(),
                    courseDTO.getStatus(),
                    courseDTO.getName(),
                    courseDTO.getDuration(),
                    courseDTO.getPrice()
            );
            courseTMS.add(courseTM);
        }
        tblCourse.setItems(courseTMS);
    }

    private void loadStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statuses);
    }
}