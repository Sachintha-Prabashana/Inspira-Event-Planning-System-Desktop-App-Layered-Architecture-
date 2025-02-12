package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDAO extends CrudDAO<Booking> {
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException ;

}
