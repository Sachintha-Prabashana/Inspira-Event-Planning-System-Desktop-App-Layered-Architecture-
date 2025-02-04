package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.BookingDto;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.entity.Service;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CreateBookingBO extends SuperBO{
    ArrayList<String> loadServiceIds() throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException ;
    String getNextBookingId() throws SQLException, ClassNotFoundException ;
    ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException ;
    CustomerDto findByCustomerId(String selectedCustomerId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadAllBookingIds() throws SQLException, ClassNotFoundException ;
    //void btnAddToBookingOnAction(ActionEvent event) ;
   // void btnCompleteBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException ;
    boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException ;
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException ;

}
