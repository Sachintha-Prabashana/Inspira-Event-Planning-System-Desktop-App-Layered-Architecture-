package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.entity.Booking;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDAO extends CrudDAO<Booking> {
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException ;

}
