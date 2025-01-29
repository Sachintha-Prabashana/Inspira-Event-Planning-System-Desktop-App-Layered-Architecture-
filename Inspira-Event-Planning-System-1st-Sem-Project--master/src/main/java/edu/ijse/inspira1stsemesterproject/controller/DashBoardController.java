package edu.ijse.inspira1stsemesterproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private AnchorPane dashBoardPane;


    @FXML
    private Label lblHome;

    @FXML
    void btnBookingOnAction(ActionEvent event) {
        loadUI("/view/BookingForm.fxml");

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        loadUI("/view/CustomerForm.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        loadUI("/view/EmployeeForm.fxml");

    }

    @FXML
    void btnEventOnAction(ActionEvent event) {

        loadUI("/view/EventForm.fxml");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        loadUI("/view/ItemForm.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadUI("/view/PaymentForm.fxml");

    }

    @FXML
    void btnServiceOnAction(ActionEvent event) {
        loadUI("/view/ServiceForm.fxml");
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        loadUI("/view/SupplierForm.fxml");
    }

    private void loadUI(String resource) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(resource));
            bodyPane.getChildren().setAll(root);
        } catch (IOException e) {
            //new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
            e.printStackTrace();
        }
    }

    public void imgUserOnClick(MouseEvent mouseEvent) {
        loadUI("/view/UserUpdateForm.fxml");

    }

    public void imgHomeOnClick(MouseEvent mouseEvent) {
        loadUI("/view/DashBoardIntroPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("/view/DashBoardIntroPage.fxml");
    }
}
