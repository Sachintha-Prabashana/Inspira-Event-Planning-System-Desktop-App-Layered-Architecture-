package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.bo.CreateBookingBO;
import edu.ijse.inspira1stsemesterproject.bo.EmployeeBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.CreateBookingBOImpl;
import edu.ijse.inspira1stsemesterproject.bo.impl.EmployeeBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> clmBookingId;

    @FXML
    private TableColumn<EmployeeTM, String> clmEmail;

    @FXML
    private TableColumn<EmployeeTM, String> clmEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> clmFirstName;

    @FXML
    private TableColumn<EmployeeTM, String> clmJobPosition;

    @FXML
    private TableColumn<EmployeeTM, Date> clmDate;

    @FXML
    private TableColumn<EmployeeTM, String> clmLastName;

    @FXML
    private TableColumn<EmployeeTM, Double> clmSalary;

    @FXML
    private JFXComboBox<String> cmbBookingId;

    @FXML
    private JFXComboBox<String> cmbJobPosition;

    @FXML
    private Label lblBookingId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDateData;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblEmployeeIdData;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblJobPosition;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblSalary;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtSalary;

    private final EmployeeBO employeeBO = new EmployeeBOImpl();
    private final CreateBookingBO bookingBo = new CreateBookingBOImpl();

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String SALARY_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbJobPosition.getItems().addAll("Event Coordinator", "Event Manager", "Event Planner", "Venue Manager", "Catering Manager", "Logistics Coordinator", "Technical Director", "Audio Visual Specialist",  "Marketing Coordinator", "Registration Coordinator","Client Relations Manager", "Event Designer");


    }

    private void setCellValues() {
        clmEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clmJobPosition.setCellValueFactory(new PropertyValueFactory<>("jobPosition"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        clmSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));

        try{
            loadBookingIds();
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try{
            refreshTable();
            String employeeId = employeeBO.getNextEmployeeId();
            lblEmployeeIdData.setText(employeeId);
        }catch (Exception e){
            e.printStackTrace();
        }

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        lblDateData.setText(formattedDate);

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = employeeBO.getAllEmployees();
        ObservableList<EmployeeTM> employeeTms = FXCollections.observableArrayList();

        for(EmployeeDto employeeDto : employeeDtos){
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDto.getEmployeeId(),
                    employeeDto.getFirstName(),
                    employeeDto.getLastName(),
                    employeeDto.getJobPosition(),
                    employeeDto.getJoinDate(),
                    employeeDto.getSalary(),
                    employeeDto.getEmail(),
                    employeeDto.getBookingId()
            );
            employeeTms.add(employeeTM);

        }
        tblEmployee.setItems(employeeTms);

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = lblEmployeeIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeBO.deleteEmployee(employeeId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Employee...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        cmbJobPosition.setValue(null);
        cmbJobPosition.setPromptText("Select job position");

        cmbBookingId.setValue(null);
        cmbBookingId.setPromptText("Select booking id");

        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        EmployeeDto employeeDto = getFieldValues();

        if(employeeDto != null) {
            try {
                boolean isSaved = employeeBO.saveEmployee(employeeDto);

                if (isSaved) {
                    showAlert("Employee saved successfully",Alert.AlertType.INFORMATION);
                    refreshPage();
                } else {
                    //new Alert(Alert.AlertType.ERROR, "Failed to save employee.").show();
                    showAlert("Failed to save employee.", Alert.AlertType.ERROR);

                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private EmployeeDto getFieldValues() {

        String employeeId = lblEmployeeIdData.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String role = cmbJobPosition.getValue();
        Date joinDate = Date.valueOf(lblDateData.getText());
        Double salary = Double.valueOf(txtSalary.getText());
        String email = txtEmail.getText();
        String bookingId = cmbBookingId.getValue();


        boolean isValidateFields = validateEmployeeFields(firstName, lastName, salary, email);

        if(isValidateFields) {
            return new EmployeeDto(employeeId,firstName,lastName,role,joinDate,salary,email,bookingId);
        }
        return null;
    }

    private boolean validateEmployeeFields(String firstName, String lastName, Double salary, String email) {
        boolean isValidName = firstName.matches(NAME_PATTERN) && lastName.matches(NAME_PATTERN);
        boolean isValidSalary = String.valueOf(salary).matches(SALARY_PATTERN);
        boolean isValidEmail = email.matches(EMAIL_PATTERN);

        if (!isValidName) {
            txtFirstName.setStyle(txtFirstName.getStyle() + ";-fx-border-color: red;");
            txtLastName.setStyle(txtLastName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidSalary) {
            txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidSalary && isValidEmail;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        EmployeeDto employeeDto = getFieldValues();

        if(employeeDto != null) {
            try{
                boolean isUpdate = employeeBO.updateCustomer(employeeDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update employee...!").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBookingIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> bookingIds = bookingBo.loadAllBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        cmbBookingId.setItems(observableList);

    }

    private void showAlert(String message , Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void tblEmployeeOnClick(MouseEvent mouseEvent) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEmployeeIdData.setText(selectedItem.getEmployeeId());
            txtFirstName.setText(selectedItem.getFirstName());
            txtLastName.setText(selectedItem.getLastName());
            cmbJobPosition.setValue(selectedItem.getJobPosition());
            lblDateData.setText(selectedItem.getJoinDate().toString());
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));
            txtEmail.setText(selectedItem.getEmail());
            cmbBookingId.setValue(selectedItem.getBookingId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
