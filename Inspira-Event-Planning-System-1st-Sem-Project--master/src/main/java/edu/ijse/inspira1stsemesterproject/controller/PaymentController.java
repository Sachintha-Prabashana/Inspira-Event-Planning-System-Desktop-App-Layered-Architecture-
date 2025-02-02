package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.CreateBookingBO;
import edu.ijse.inspira1stsemesterproject.bo.PaymentBo;
import edu.ijse.inspira1stsemesterproject.bo.impl.CreateBookingBOImpl;
import edu.ijse.inspira1stsemesterproject.bo.impl.PaymentBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.PaymentDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.PaymentTM;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbBookingId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TableColumn<PaymentTM, Double> clmAmount;

    @FXML
    private TableColumn<PaymentTM, String> clmBookingId;

    @FXML
    private TableColumn<PaymentTM, Date> clmPayedDate;

    @FXML
    private TableColumn<PaymentTM, String> clmPaymentId;

    @FXML
    private TableView<PaymentTM> tblPayment;

    private static final String DATE_PATTERN = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private static final String AMOUNT_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";


    private final PaymentBOImpl paymentBo = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    private final CreateBookingBOImpl createBookingBO = (CreateBookingBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CREATE_BOOKING);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try{
            refreshPage();
            loadBookingIds();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCellValues() {
        clmPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        clmPayedDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        clmBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    }

    private void refreshPage() throws Exception{
        refreshTable();

        try{
            String paymentId = paymentBo.getNextPaymentId();
            lblPaymentId.setText(paymentId);
        }catch (Exception e){
            e.printStackTrace();
        }

        lblDate.setText(LocalDate.now().toString());
        txtAmount.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDto> paymentDtos = paymentBo.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for(PaymentDto paymentDto : paymentDtos){
            PaymentTM paymentTM = new PaymentTM(
                    paymentDto.getPaymentId(),
                    paymentDto.getPaymentDate(),
                    paymentDto.getPaymentAmount(),
                    paymentDto.getBookingId()
            );
            paymentTMS.add(paymentTM);

        }
        tblPayment.setItems(paymentTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String paymentId = lblPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBo.deletePayment(paymentId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete payment...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        cmbBookingId.setValue(null);
        cmbBookingId.setPromptText("Select Booking Id");

        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        PaymentDto paymentDto = getFieldValues();

        if(paymentDto != null) {
            try {
                boolean isSaved = paymentBo.savePayment(paymentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save payment.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private PaymentDto getFieldValues() {
        String paymentId = lblPaymentId.getText();
        String bookingId = cmbBookingId.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(lblDate.getText());
        Double amount = Double.valueOf(txtAmount.getText());

        boolean isValidateFields = validatePaymentFields();

        if(isValidateFields) {
            return new PaymentDto(paymentId,date,amount,bookingId);
        }
        return null;

    }

    private boolean validatePaymentFields() {
        boolean isvalidDate = lblDate.getText().matches(DATE_PATTERN);

        if(!isvalidDate) {
            lblDate.setStyle(lblDate.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidAmount = txtAmount.getText().matches(AMOUNT_PATTERN);

        if(!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
        }

        return isvalidDate && isValidAmount;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        PaymentDto paymentDto = getFieldValues();

        if(paymentDto != null) {
            try {
                boolean isSaved = paymentBo.updatePayment(paymentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment update successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update payment.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void loadBookingIds() throws ClassNotFoundException, SQLException {
        ArrayList<String> bookingIds = createBookingBO.loadAllBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        cmbBookingId.setItems(observableList);
    }

    public void tblPaymentOnClick(MouseEvent mouseEvent) {
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPaymentId.setText(selectedItem.getPaymentId());
            cmbBookingId.setValue(selectedItem.getBookingId());
            lblDate.setText(String.valueOf(selectedItem.getPaymentDate()));
            txtAmount.setText(selectedItem.getPaymentAmount().toString());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
