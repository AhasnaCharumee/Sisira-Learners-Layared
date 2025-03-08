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
import lk.ijse.gdse72.sisiralearners.bo.custom.ExamFormBO;
import lk.ijse.gdse72.sisiralearners.dto.ExamDTO;
import lk.ijse.gdse72.sisiralearners.dto.tm.ExamTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExamFormController implements Initializable {

    private ExamFormBO examFormBO = (ExamFormBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.EXAM);
    @FXML
    private DatePicker DatePickerExamDate;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbResult;

    @FXML
    private JFXComboBox<String> cmbStudentNames;

    @FXML
    private TableColumn<ExamTM, String> colExamDate;

    @FXML
    private TableColumn<ExamTM, String> colExamId;

    @FXML
    private TableColumn<ExamTM, String> colExamName;

    @FXML
    private TableColumn<ExamTM, String> colResult;

    @FXML
    private TableColumn<ExamTM, String> colStudentName;

    @FXML
    private AnchorPane paneExam;

    @FXML
    private TableView<ExamTM> tblExam;

    @FXML
    private JFXTextField txtExamName;

    @FXML
    private JFXTextField txtExamId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String examId = txtExamId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this exam?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = examFormBO.deleteExam(examId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Exam deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete exam!").show();
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
        String examId = txtExamId.getText();
        String examName = txtExamName.getText();
        String studentName = cmbStudentNames.getValue();
        java.sql.Date examDate = java.sql.Date.valueOf(DatePickerExamDate.getValue());
        String result = cmbResult.getValue();

        try {
            if (examFormBO.existExam(examId)) {
                new Alert(Alert.AlertType.WARNING, "Exam ID already exists!").show();
                return;
            }
            String studentId = examFormBO.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            ExamDTO examDTO = new ExamDTO(examId, examName, studentId, examDate, result);
            boolean isSaved = examFormBO.saveExam(examDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Exam saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save exam!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the exam: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String examId = txtExamId.getText();
        String examName = txtExamName.getText();
        String studentName = cmbStudentNames.getValue();
        java.sql.Date examDate = java.sql.Date.valueOf(DatePickerExamDate.getValue());
        String result = cmbResult.getValue();

        try {
            String studentId = examFormBO.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            ExamDTO examDTO = new ExamDTO(examId, examName, studentId, examDate, result);
            boolean isUpdated = examFormBO.updateExam(examDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Exam updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update exam!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the exam: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblExamOnClicked(MouseEvent event) {
        ExamTM selectedItem = tblExam.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtExamId.setText(selectedItem.getExam_id());
            txtExamName.setText(selectedItem.getExam_name());
            cmbStudentNames.getSelectionModel().select(selectedItem.getStudent_name());
            DatePickerExamDate.setValue(selectedItem.getExam_date().toLocalDate());
            cmbResult.getSelectionModel().select(selectedItem.getResult());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colExamId.setCellValueFactory(new PropertyValueFactory<>("exam_id"));
        colExamName.setCellValueFactory(new PropertyValueFactory<>("exam_name"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colExamDate.setCellValueFactory(new PropertyValueFactory<>("exam_date"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        try {
            refreshPage();
            loadResults();
            loadStudentNames();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextExamId = examFormBO.getNextExamId();
        txtExamId.setText(nextExamId);

        txtExamName.setText("");
        DatePickerExamDate.setValue(null);
        cmbResult.getSelectionModel().clearSelection();
        cmbStudentNames.getSelectionModel().clearSelection();
        loadResults();
        loadStudentNames();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<ExamDTO> examDTOS = examFormBO.getAllExams();
        ObservableList<ExamTM> examTMS = FXCollections.observableArrayList();

        for (ExamDTO examDTO : examDTOS) {
            String studentName = examFormBO.getAllStudents().stream()
                    .filter(student -> student.getStudent_id().equals(examDTO.getStudent_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getName();

            ExamTM examTM = new ExamTM(
                    examDTO.getExam_id(),
                    examDTO.getExam_name(),
                    studentName,
                    examDTO.getExam_date(),
                    examDTO.getResult()
            );
            examTMS.add(examTM);
        }
        tblExam.setItems(examTMS);
    }

    private void loadResults() {
        ObservableList<String> results = FXCollections.observableArrayList("Pass", "Fail","Absent","Pending");
        cmbResult.setItems(results);
    }

    private void loadStudentNames() throws SQLException {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        studentNames.addAll(examFormBO.getAllStudentNames());
        cmbStudentNames.setItems(studentNames);
    }
}