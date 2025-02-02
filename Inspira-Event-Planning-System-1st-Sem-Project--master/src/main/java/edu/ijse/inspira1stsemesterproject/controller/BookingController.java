package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.CreateBookingBO;
import edu.ijse.inspira1stsemesterproject.bo.CustomerBO;
import edu.ijse.inspira1stsemesterproject.bo.ServiceBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.CreateBookingBOImpl;
import edu.ijse.inspira1stsemesterproject.bo.impl.CustomerBOImpl;
import edu.ijse.inspira1stsemesterproject.bo.impl.ServiceBOImpl;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.BookingDto;
import edu.ijse.inspira1stsemesterproject.dto.BookingServiceDto;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.view.tdm.BookingTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmAction;

    @FXML
    private TableColumn<BookingTM, Date> clmBookingData;

    @FXML
    private TableColumn<BookingTM, String> clmBookingId;

    @FXML
    private TableColumn<BookingTM, Integer> clmCapacity;

    @FXML
    private TableColumn<BookingTM, String> clmCustomerId;

    @FXML
    private TableColumn<BookingTM, String> clmServiceId;

    @FXML
    private TableColumn<BookingTM, String> clmVenue;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbServiceId;

    @FXML
    private Label lblBookingId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblServiceType;

    @FXML
    private TableView<BookingTM> tblBooking;

    @FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtVenue;

    private final CreateBookingBOImpl createBookingBO = (CreateBookingBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CREATE_BOOKING);
    private final CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);


    private final ObservableList<BookingTM> bookingTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void setCellValues() {
        clmBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        clmCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        clmCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        clmVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        clmBookingData.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        clmAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        tblBooking.setItems(bookingTMS);
    }


    private void refreshPage() throws SQLException, ClassNotFoundException {
        // Get the next order ID and set it to the label
        lblBookingId.setText(createBookingBO.getNextBookingId());


        lblDate.setText(LocalDate.now().toString());

        // Load customer and item IDs into ComboBoxes
        loadCustomerIds();
        loadServiceIds();


        // Clear selected customer, item, and their associated labels
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbServiceId.getSelectionModel().clearSelection();
        lblCustomerName.setText("");
        lblServiceType.setText("");
        txtCapacity.setText("");
        txtVenue.setText("");

        // Clear the cart observable list
        bookingTMS.clear();

        // Refresh the table to reflect changes
        tblBooking.refresh();

    }

    private void loadServiceIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> serviceIds = createBookingBO.loadServiceIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(serviceIds);
        cmbServiceId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIds = createBookingBO.loadCustomerIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    @FXML
    public void btnAddToBookingOnAction(ActionEvent event) {
        String selectedCustomerId = cmbCustomerId.getValue();

        if (selectedCustomerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select customer..!").show();
            return;
        }

        String selectedServiceId = cmbServiceId.getValue();

        if (selectedServiceId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select service..!").show();
            return;
        }

        String capacityPattern = "^[0-9]+$";

        boolean isValidCapacity = txtCapacity.getText().matches(capacityPattern);

        if (!isValidCapacity){
            new Alert(Alert.AlertType.ERROR,"Invalid capacity").show();
            return;
        }

        String venuePattern = "^[A-Za-z ]+$";

        boolean isValidVenue = txtVenue.getText().matches(venuePattern);

        if (!isValidVenue){
            new Alert(Alert.AlertType.ERROR,"Invalid venue").show();
            return;
        }

        String customerName = lblCustomerName.getText();
        int capacity = Integer.parseInt(txtCapacity.getText());
        String serviceType = lblServiceType.getText();
        String venue = txtVenue.getText();
        Date date = Date.valueOf(lblDate.getText());


        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        BookingTM newBookingTM = new BookingTM(
                lblBookingId.getText(),
                selectedCustomerId,
                selectedServiceId,
                capacity,
                venue,
                date,
                btn
        );

        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            bookingTMS.remove(newBookingTM);

            // Refresh the table to reflect the removal of the item.
            tblBooking.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        bookingTMS.add(newBookingTM);

    }

    @FXML
    public void btnCompleteBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (tblBooking.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to table..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place booking..!").show();
            return;
        }

        String bookingId = lblBookingId.getText();
        int capacity = Integer.parseInt(txtCapacity.getText());
        String venue = txtVenue.getText();
        Date dateOfBooking = Date.valueOf(lblDate.getText());
        String customerId = cmbCustomerId.getValue();
        String serviceId = cmbServiceId.getValue();
        String status = "available";

        // List to hold order details
        ArrayList<BookingServiceDto> bookingServiceDtos = new ArrayList<>();

        // Collect data for each item in the cart and add to order details array
        for (BookingTM bookingTM : bookingTMS) {

            // Create order details for each cart item
            BookingServiceDto bookingServiceDto = new BookingServiceDto(
                    bookingId,
                    bookingTM.getServiceId(),
                    dateOfBooking
            );

            // Add to order details array
            bookingServiceDtos.add(bookingServiceDto);
        }

        // Create an OrderDTO with relevant order data
        BookingDto bookingDto = new BookingDto(
                bookingId,
                customerId,
                capacity,
                venue,
                dateOfBooking,
                status,
                bookingServiceDtos
        );

        boolean saveBooking = saveBooking(bookingDto);

        if (saveBooking) {
            new Alert(Alert.AlertType.INFORMATION, "Booking successfully added!").show();
        }



    }

    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        boolean isSaved = createBookingBO.saveBooking(bookingDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved with status 'available'..!").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save booking..!").show();
        }
        return isSaved;

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        cmbCustomerId.setValue(null);
        cmbCustomerId.setPromptText("Select customer id");

        cmbServiceId.setValue(null);
        cmbServiceId.setPromptText("Select service id");

        refreshPage();
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDto customerDto = customerBO.findById(selectedCustomerId);

        // If customer found (customerDTO not null)
        if (customerDto != null) {

            // FIll customer related labels
            lblCustomerName.setText(customerDto.concatName());
        }

    }

    @FXML
    void cmbServiceIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedServiceId = cmbServiceId.getSelectionModel().getSelectedItem();
        ServiceDto serviceDto = createBookingBO.findById(selectedServiceId);

        if (serviceDto != null) {

            lblServiceType.setText(serviceDto.getServiceType());
        }
    }


    public void btnGenerateReportOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/BookingServiceReport.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
