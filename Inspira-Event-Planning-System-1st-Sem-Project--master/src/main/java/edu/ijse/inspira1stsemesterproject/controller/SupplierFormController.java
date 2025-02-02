package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.SupplierBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.SupplierBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.SupplierTM;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> clmEmail;

    @FXML
    private TableColumn<SupplierTM, String> clmSupplierId;

    @FXML
    private TableColumn<SupplierTM, String> clmSupplierName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblSupplierId;


    @FXML
    private Label lblSupplierIdData;

    SupplierBOImpl supplierBO = (SupplierBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellvalues();
    }

    private void setCellvalues() {
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void refreshPage() {
        try{
            refreshTable();
            String supplierId = supplierBO.getNextSupplierId();
            lblSupplierIdData.setText(supplierId);

        }catch (Exception e){
            e.printStackTrace();
        }

        txtSupplierName.setText("");
        txtEmail.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDtos = supplierBO.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getEmail()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMS);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = lblSupplierIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(supplierId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "supplier deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        SupplierDto supplierDto = getFieldValues();

        if(supplierDto != null) {
            try {
                boolean isSaved = supplierBO.saveSupplier(supplierDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save supplier.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        SupplierDto supplierDto = getFieldValues();

        if(supplierDto != null) {
            try {
                boolean isUpdate = supplierBO.updateSupplier(supplierDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update supplier.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private SupplierDto getFieldValues() {
        String supplierId = lblSupplierIdData.getText();
        String supplierName = txtSupplierName.getText();
        String email = txtEmail.getText();

        boolean isValidateFields = validateSupplierFields();

        if(isValidateFields) {
            return new SupplierDto(supplierId,supplierName,email);
        }
        return null;

    }

    private boolean validateSupplierFields() {
        boolean isvalidName = txtSupplierName.getText().matches(NAME_PATTERN);

        if(!isvalidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidEmail = txtEmail.getText().matches(EMAIL_PATTERN);

        if(!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        return isvalidName && isValidEmail;
    }


    public void tblSupplierOnClick(MouseEvent mouseEvent) {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSupplierIdData.setText(selectedItem.getSupplierId());
            txtSupplierName.setText(selectedItem.getSupplierName());
            txtEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnSendMailOnAction(ActionEvent actionEvent) {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a supplier first!..!");
            alert.showAndWait();
            return;
        }

        try{
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setSupplierEmail(email);

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
}
