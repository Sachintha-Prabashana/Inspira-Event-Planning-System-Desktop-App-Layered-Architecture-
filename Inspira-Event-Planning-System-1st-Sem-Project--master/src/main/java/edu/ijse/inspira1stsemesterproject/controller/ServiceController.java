package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.ServiceBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.ServiceBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.ServiceTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ServiceTM, Double> clmPrice;

    @FXML
    private TableColumn<ServiceTM, String> clmServiceId;

    @FXML
    private TableColumn<ServiceTM, String> clmServiceType;

    @FXML
    private JFXComboBox<String> cmbServiceType;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblServiceId;

    @FXML
    private Label lblServiceIdData;

    @FXML
    private Label lblServiceType;

    @FXML
    private TableView<ServiceTM> tblService;

    @FXML
    private TextField txtPrice;

    private final ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);

    private static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbServiceType.getItems().addAll("Cleaning", "Catering", "Security", "Lighting", "Photography", "Decoration");


        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void setCellValues() {
        clmServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
    }

    private void refreshPage() throws Exception{
        refreshTable();

        try{
            String serviceId = serviceBO.getNextServiceId();
            lblServiceIdData.setText(serviceId);
        }catch(Exception e){
            e.printStackTrace();
        }
        txtPrice.setText("");
        //cmbServiceType.getItems().clear();

        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);


    }

    private void refreshTable() throws SQLException, ClassNotFoundException {

        ArrayList<ServiceDto> serviceDtos = serviceBO.getAllServices();
        ObservableList<ServiceTM> serviceTMS= FXCollections.observableArrayList();

        for (ServiceDto serviceDto : serviceDtos) {
            ServiceTM serviceTM = new ServiceTM(
                    serviceDto.getServiceId(),
                    serviceDto.getServiceType(),
                    serviceDto.getPrice()
            );
            serviceTMS.add(serviceTM);
        }
        tblService.setItems(serviceTMS);

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String serviceId = lblServiceIdData.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = serviceBO.deleteService(serviceId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "service deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete service...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        cmbServiceType.setValue(null);
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        ServiceDto serviceDto = getFieldValues();

        if(serviceDto != null) {
            try {
                boolean isSaved = serviceBO.saveService(serviceDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Service saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save service.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private ServiceDto getFieldValues(){
        String serviceId = lblServiceIdData.getText();
        String serviceType = cmbServiceType.getSelectionModel().getSelectedItem();
        double price = Double.parseDouble(txtPrice.getText());

        boolean isValidateFields = validateServiceFields();

        if(isValidateFields) {
            return new ServiceDto(serviceId,price,serviceType);
        }
        return null;


    }

    private boolean validateServiceFields() {
        boolean isValidPrice = txtPrice.getText().matches(PRICE_PATTERN);

        if(!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        return isValidPrice;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ServiceDto serviceDto = getFieldValues();

        if(serviceDto != null) {
            try {
                boolean isUpdate = serviceBO.updateService(serviceDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Service updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update service.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void tblServiceOnAction(MouseEvent mouseEvent) {
        ServiceTM selectedItem = tblService.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblServiceIdData.setText(selectedItem.getServiceId());
            cmbServiceType.setValue(selectedItem.getServiceType());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
