package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CompleteEventBO extends SuperBO{
    boolean completeEventCreation(EventDto eventDto) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadSupplierIds() throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadBookingIds() throws SQLException, ClassNotFoundException ;
    ItemDto findByItemId(String itemId) throws SQLException, ClassNotFoundException ;
    SupplierDto findBySupplierId(String supplierId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadItemIDs(String supplierId) throws SQLException, ClassNotFoundException ;
    String getNextEventId() throws SQLException, ClassNotFoundException ;
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException;
    boolean isVenueAvailable(String venue, Date date) throws SQLException, ClassNotFoundException;
}
