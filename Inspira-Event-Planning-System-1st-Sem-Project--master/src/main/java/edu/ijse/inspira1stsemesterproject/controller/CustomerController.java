package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.CustomerBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.CustomerBOImpl;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerController implements Initializable {

    @FXML
    private Button btnCustomerRepo;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateRepo;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> clmCustId;

    @FXML
    private TableColumn<CustomerTM, Date> clmDate;

    @FXML
    private TableColumn<CustomerTM, String> clmEmail;

    @FXML
    private TableColumn<CustomerTM, String> clmFirstName;

    @FXML
    private TableColumn<CustomerTM, String> clmLastName;

    @FXML
    private TableColumn<CustomerTM, String> clmNic;

    @FXML
    private TableColumn<CustomerTM, String> clmTitle;

    @FXML
    private ComboBox<String> cmbCustTitle;

    @FXML
    private Label lblCustId;

    @FXML
    private Label lblCustIdData;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDateData;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtNic;

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String NIC_PATTERN = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbCustTitle.getItems().addAll("Mr", "Ms", "Mrs", "Dr", "Prof","Miss");
    }

    private void setCellValues(){
        clmCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        clmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clmNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {

        try {
            refreshTable();
            String nextCustomerID = customerBO.getNextCustomerId();
            lblCustIdData.setText(nextCustomerID);
        }catch (Exception e) {
            e.printStackTrace();
        }


        // Getting today's date
        LocalDate today = LocalDate.now();

// Formatting date as needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        lblDateData.setText(formattedDate);
        //cmbCustTitle.getItems().clear();


        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        //btnCustomerRepo.setDisable(true);
       // btnGenerateRepo.setDisable(true);
        //btnOpenMailSendModel.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = customerBO.getAllCustomer();
        ObservableList<CustomerTM> customerTms = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            // String formattedDate = dateFormat.format(customerDto.getRegistrationDate());

            CustomerTM customerTm = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerTitle(),
                    customerDto.getFirstName(),
                    customerDto.getLastName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getRegistrationDate()
            );
            customerTms.add(customerTm);
        }
        tblCustomer.setItems(customerTms);
    }

    @FXML
    void btnCustomerRepoOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

//            Map<String, Object> parameters = new HashMap<>();
//            today - 2024 - 02 - 02
//            TODAY -

//            parameters.put("today",LocalDate.now().toString());
//            <key , value>
//            Initialize a map to hold the report parameters
//            These parameters can be used inside the report (like displaying today's date)

            // Initialize a map to hold the report parameters
            // These parameters can be used inside the report (like displaying today's date)
            Map<String, Object> parameters = new HashMap<>();

            // Put the current date into the map with two different keys ("today" and "TODAY")
            // You can refer to these keys in the Jasper report if needed
            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());
            // Replace VariableName with the actual variable name


            // Compile the Jasper report from a JRXML file (report template)
            // The report template is located in the "resources/report" folder of the project
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/CustomerReport.jrxml"));

            // Fill the report with the compiled report object, parameters, and a database connection
            // This prepares the report with real data from the database
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            // Display the report in a viewer (this is a built-in JasperReports viewer)
            // 'false' indicates that the window should not close the entire application when closed
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerBO.deleteCustomer(customerId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Customer...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if(customerDto != null) {
            try {
                boolean isSaved = customerBO.saveCustomer(customerDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                    refreshPage();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save customer.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private CustomerDto getFieldValues() {
        String customerId = lblCustIdData.getText();
        String customerTitle = cmbCustTitle.getSelectionModel().getSelectedItem();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        LocalDate localEventDate = LocalDate.parse(lblDateData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date registrationDate = Date.valueOf(localEventDate);

        boolean isValidateFields = validateCustomerFields(firstName, lastName, nic, email);

        if(isValidateFields) {
            return new CustomerDto(customerId,customerTitle,firstName,lastName,nic,email,registrationDate);
        }
        return null;

    }

    private boolean validateCustomerFields(String firstName, String lastName, String nic, String email) {
        boolean isValidName = firstName.matches(NAME_PATTERN) && lastName.matches(NAME_PATTERN);
        boolean isValidNic = nic.matches(NIC_PATTERN);
        boolean isValidEmail = email.matches(EMAIL_PATTERN);

        if (!isValidName) {
            txtFirstName.setStyle(txtFirstName.getStyle() + ";-fx-border-color: red;");
            txtLastName.setStyle(txtLastName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidNic && isValidEmail;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if(customerDto != null) {
            try{
                boolean isUpdate = customerBO.updateCustomer(customerDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnSendMailOnAction(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer first!");
            alert.showAndWait();
            return;
        }

        try{
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblCustomerOnClick(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustIdData.setText(selectedItem.getCustomerId());
            cmbCustTitle.setValue(selectedItem.getCustomerTitle());
            txtFirstName.setText(selectedItem.getFirstName());
            txtLastName.setText(selectedItem.getLastName());
            txtNic.setText(selectedItem.getNic());
            txtEmail.setText(selectedItem.getEmail());
            lblDateData.setText(selectedItem.getRegistrationDate().toString());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnCustomerRepo.setDisable(false);
        }
    }
}
