package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.BookingServiceDAO;
import edu.ijse.inspira1stsemesterproject.dto.BookingServiceDto;
import edu.ijse.inspira1stsemesterproject.entity.BookingService;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookingServiceDAOImpl implements BookingServiceDAO {
    public boolean saveBookingServiceList(ArrayList<BookingService> bookingServices) throws SQLException, ClassNotFoundException {
        for (BookingService entity : bookingServices) {
            try {
                // Save individual booking service details
                boolean isOrderDetailsSaved = saveBookingServiceDetails(entity);
                if (!isOrderDetailsSaved) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveBookingServiceDetails(BookingService entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO BookingService (booking_id, service_id, date) VALUES (?, ?, ?)",  // Match the column names if needed
                entity.getBookingId(),
                entity.getServiceId(),
                entity.getBookingDate()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<BookingService> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BookingService entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BookingService entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BookingService findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
