package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.BookingDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.BookingServiceDAO;
import edu.ijse.inspira1stsemesterproject.entity.Booking;
import edu.ijse.inspira1stsemesterproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    BookingServiceDAO bookingServiceDAO = new BookingServiceDAOImpl();

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select booking_id from booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }


    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select booking_id from booking order by booking_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "B001";
    }


    public boolean save(Booking entity) throws SQLException, ClassNotFoundException {


        boolean isBookingSaved = SQLUtil.execute(
                "INSERT INTO booking (booking_id, customer_id, capacity, venue, booking_date, status) VALUES (?, ?, ?, ?, ?, ?)",
                    entity.getBookingId(),
                    entity.getCustomerId(),
                    entity.getCapacity(),
                    entity.getVenue(),
                    entity.getBookingDate(),
                    "available" // Default status for a new booking
        );
        return isBookingSaved;


    }

    @Override
    public boolean delete(String bookingId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Booking> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE booking SET status = ? WHERE booking_id = ?", "used", bookingId);

    }


    public ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM booking WHERE status = 'available'");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }
}
