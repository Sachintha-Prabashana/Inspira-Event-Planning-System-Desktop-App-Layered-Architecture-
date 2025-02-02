package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXComboBox;
import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.CompleteEventBO;
import edu.ijse.inspira1stsemesterproject.bo.SupplierBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.CompleteEventBOImpl;
import edu.ijse.inspira1stsemesterproject.bo.impl.SupplierBOImpl;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.*;
import edu.ijse.inspira1stsemesterproject.view.tdm.EventTM;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EventController implements Initializable {

    @FXML
    private TableColumn<?, ?> clmAction;

    @FXML
    private TableColumn<EventTM, Double> clmBudget;

    @FXML
    private TableColumn<EventTM, Date> clmDate;

    @FXML
    private TableColumn<EventTM, String> clmEventId;

    @FXML
    private TableColumn<EventTM, String> clmEventName;

    @FXML
    private TableColumn<EventTM, String> clmEventType;

    @FXML
    private TableColumn<EventTM, String> clmItemName;

    @FXML
    private TableColumn<EventTM, Integer> clmQty;

    @FXML
    private TableColumn<EventTM, String> clmSupplierId;

    @FXML
    private TableColumn<EventTM, Double> clmTotal;

    @FXML
    private TableColumn<EventTM, Double> clmUnitprice;

    @FXML
    private TableColumn<?, ?> clmBookingId;

    @FXML
    private TableColumn<EventTM, String> clmVenue;

    @FXML
    private JFXComboBox<String> cmbBookingId;

    @FXML
    private JFXComboBox<String> cmbEventType;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private Label lblEventId;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<EventTM> tblEvent;

    @FXML
    private TextField txtBudget;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEventName;

    @FXML
    private TextField txtQtyToAdd;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField txtVenue;

    private static final String VENUE_PATTERN = "^[A-Za-z0-9 ,.-]+$";
    private static final String QTY_PATTERN = "^[1-9][0-9]*$";
    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";


   // private final  SupplierBOImpl supplierBO = (SupplierBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    CompleteEventBOImpl completeEventBO = (CompleteEventBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.COMPLETE_EVENT);

    private final ObservableList<EventTM> eventTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        cmbEventType.getItems().addAll("Wedding", "Conference", "Birthday Party", "Corporate Event", "Workshop", "Concert", "Exhibition", "Seminar", "Fundraiser", "Festival", "Gala", "Product Launch", "Retreat", "Trade Show", "Networking Event", "Training Session");


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setCellValues() {
        clmEventId.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        clmBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        clmEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        clmEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        clmBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        clmVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        clmAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        tblEvent.setItems(eventTMS);

    }

    private void refreshPage() throws Exception {

        lblEventId.setText(completeEventBO.getNextEventId());

        loadBookingIds();
        loadSupplierIds();

        cmbEventType.getSelectionModel().clearSelection();
        cmbBookingId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        cmbSupplierId.getSelectionModel().clearSelection();
        lblPrice.setText("");
        lblItemName.setText("");
        lblSupplierName.setText("");
        lblQty.setText("");
        txtVenue.setText("");
        eventDatePicker.setValue(null);
        txtBudget.setText("");
        txtEventName.setText("");
        txtQtyToAdd.setText("");

        // Clear the cart observable list
        eventTMS.clear();

        // Refresh the table to reflect changes
        tblEvent.refresh();

    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String selectedBookingId = cmbBookingId.getValue();

        if (selectedBookingId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select booking..!").show();
            return;
        }

        String selectedEventType = cmbEventType.getValue();

        if (selectedEventType == null) {
            new Alert(Alert.AlertType.ERROR, "Please select event type..!").show();
        }

        String selectedSupplierId = cmbSupplierId.getValue();

        if (selectedSupplierId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select supplier..!").show();
            return;
        }

        String selectedItemId = cmbItemId.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return;
        }

        boolean isValidName = txtEventName.getText().matches(NAME_PATTERN);

        if (!isValidName) {
            txtEventName.setStyle(txtEventName.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidQty = txtQtyToAdd.getText().matches(QTY_PATTERN);
        if (!isValidQty) {
            txtQtyToAdd.setStyle(txtQtyToAdd.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidVenue = txtVenue.getText().matches(VENUE_PATTERN);
        if (!isValidVenue) {
            txtVenue.setStyle(txtVenue.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidBudget = txtBudget.getText().matches(PRICE_PATTERN);

        if (!isValidBudget) {
            txtBudget.setStyle(txtBudget.getStyle() + ";-fx-border-color: red;");
        }

        if (eventDatePicker.getValue() == null || eventDatePicker.getValue().isBefore(LocalDate.now())) {
            new Alert(Alert.AlertType.ERROR, "Invalid date. Please select a future date..!").show();
            return;
        }

        String eventType = cmbEventType.getValue();
        int quantity = Integer.parseInt(lblQty.getText());
        int qty = Integer.parseInt(txtQtyToAdd.getText());
        Double budget = Double.valueOf((txtBudget.getText()));
        String eventName = txtEventName.getText();
        String venue = txtVenue.getText();
        java.sql.Date date = java.sql.Date.valueOf(eventDatePicker.getValue());
        String itemName = lblItemName.getText();
        Double price = Double.valueOf(lblPrice.getText());

        if (quantity < qty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return; // Exit the method if there's insufficient stock.
        }
        
        if(quantity <= 0){
            new Alert(Alert.AlertType.ERROR, "Add items to database..!").show();
        }

        // Clear the text field for adding quantity after retrieving the input value.
        txtQtyToAdd.setText("");

        double unitPrice = Double.parseDouble(lblPrice.getText());
        double total = unitPrice * qty;

        for (EventTM eventTM : eventTMS) {

            // Check if the item is already in the cart
            if (eventTM.getItemId().equals(selectedItemId)) {
                // Update the existing CartTM object in the cart's observable list with the new quantity and total.
                int newQty = eventTM.getQuantity() + qty;
                eventTM.setQuantity(newQty); // Add the new quantity to the existing quantity in the cart.
                eventTM.setTotalPrice(unitPrice * newQty); // Recalculate the total price based on the updated quantity

                // Refresh the table to display the updated information.
                tblEvent.refresh();
                return; // Exit the method as the cart item has been updated.
            }
        }
        // Create a "Remove" button for the item to allow it to be removed from the cart later.
        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        EventTM newEventTm = new EventTM(
                lblEventId.getText(),
                selectedBookingId,
                selectedItemId,
                eventType,
                eventName,
                budget,
                venue,
                date,
                selectedSupplierId,
                itemName,
                qty,
                unitPrice,
                total,
                btn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            eventTMS.remove(newEventTm);

            // Refresh the table to reflect the removal of the item.
            tblEvent.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        eventTMS.add(newEventTm);


    }


    public void loadSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierIds = completeEventBO.loadSupplierIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        cmbSupplierId.setItems(observableList);
    }

    public void loadBookingIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> bookingIds = completeEventBO.loadBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        cmbBookingId.setItems(observableList);
    }


    @FXML
    void cmbItemIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        findByItemId();

    }

    void findByItemId() throws SQLException, ClassNotFoundException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDto itemDto = completeEventBO.findByItemId(selectedItemId);

        if (itemDto != null) {

            lblItemName.setText(itemDto.getItemName());
            lblPrice.setText(String.valueOf(itemDto.getItemPrice()));
            lblQty.setText(String.valueOf(itemDto.getItemQuantity()));
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        findBySupplierId();
    }

    void findBySupplierId() throws SQLException, ClassNotFoundException {
        String selectedSupplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
        SupplierDto supplierDto = completeEventBO.findBySupplierId(selectedSupplierId);

        if (supplierDto != null) {

            lblSupplierName.setText(supplierDto.getSupplierName());
            loadItemIDs(selectedSupplierId);
        }
    }

    private void loadItemIDs(String supplierId) throws SQLException, ClassNotFoundException {
        ArrayList<String> itemIds = completeEventBO.loadItemIDs(supplierId);

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    public void blnCompleteSetupOnAction(ActionEvent event) throws Exception {
        if (tblEvent.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to the table..!").show();
            return;
        }
        if (cmbBookingId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a booking to place the event..!").show();
            return;
        }

        String eventId = lblEventId.getText();
        String eventType = cmbEventType.getValue();
        Double budget = Double.valueOf(txtBudget.getText());
        String eventName = txtEventName.getText();
        String venue = txtVenue.getText();
        java.sql.Date date = java.sql.Date.valueOf(eventDatePicker.getValue());

        boolean isVenueAvailable = completeEventBO.isVenueAvailable(venue, date);
        if (!isVenueAvailable) {
            new Alert(Alert.AlertType.ERROR, "The selected venue is already booked for the selected date.").show();
            return;
        }

        ArrayList<EventSupplierDto> eventSupplierDtos = new ArrayList<>();

        for (EventTM eventTM : eventTMS) {
            if (eventTM.getSupplierId() == null || eventTM.getItemId() == null || eventTM.getQuantity() <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid data in the event table. Please check the inputs.").show();
                return;
            }

            EventSupplierDto eventSupplierDto = new EventSupplierDto(
                    eventId,
                    eventTM.getSupplierId(),
                    eventTM.getItemId(),
                    eventTM.getQuantity(),
                    eventTM.getTotalPrice()
            );
            eventSupplierDtos.add(eventSupplierDto);
        }

        EventDto eventDto = new EventDto(
                eventId,
                cmbBookingId.getValue(),
                eventType,
                eventName,
                budget,
                venue,
                date,
                eventSupplierDtos
        );

        completeEventSetUp(eventDto);


    }

    public void completeEventSetUp(EventDto eventDto) throws Exception {
//        boolean isSaved = completeEventSetUp(eventDto);

        boolean isSaved = completeEventBO.completeEventCreation(eventDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Event saved successfully..!").show();
            // After event is saved, update the booking status to 'used'
            String bookingId = cmbBookingId.getValue();
            boolean isBookingStatusUpdated = completeEventBO.updateBookingStatusToUsed(bookingId);

            if (isBookingStatusUpdated) {
                System.out.println("Booking status updated to 'used' for booking ID: " + bookingId);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update booking status to 'used'.").show();
            }

            // Refresh the page
            refreshPage();

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save event. Please check item details.").show();
        }
    }


    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/EventSupplier.jrxml"));

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

    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        refreshPage();
    }
}
