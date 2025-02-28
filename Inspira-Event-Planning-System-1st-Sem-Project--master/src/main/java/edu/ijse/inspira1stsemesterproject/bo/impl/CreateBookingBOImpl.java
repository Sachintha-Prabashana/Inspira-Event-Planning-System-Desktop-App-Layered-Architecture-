package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.CreateBookingBO;
import edu.ijse.inspira1stsemesterproject.bo.CustomerBO;
import edu.ijse.inspira1stsemesterproject.bo.ServiceBO;
import edu.ijse.inspira1stsemesterproject.dao.DAOFactory;
import edu.ijse.inspira1stsemesterproject.dao.custom.BookingDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.BookingServiceDAO;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.BookingDto;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.entity.Booking;
import edu.ijse.inspira1stsemesterproject.entity.BookingService;

import java.sql.*;
import java.util.ArrayList;

public class CreateBookingBOImpl implements CreateBookingBO {

    BookingServiceDAO bookingServiceDAO = (BookingServiceDAO) DAOFactory.getInstance().getDao(DAOFactory.DAOType.BOOKING_SERVICE);
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getInstance().getDao(DAOFactory.DAOType.BOOKING);
    //ServiceDAO serviceDAO = new ServiceDAOImpl();
    ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);
    //CustomerDAO customerDAO = new CustomerDAOImpl();
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    public ArrayList<String> loadServiceIds() throws SQLException, ClassNotFoundException {
        return serviceBO.getAllServiceIds();
    }

    public ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        return customerBO.getAllCustomerIds();
    }

    public ArrayList<String> loadAllBookingIds() throws SQLException, ClassNotFoundException {
        return bookingDAO.getAllIds();
    }

    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        return bookingDAO.getNextId();
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        return serviceBO.findById(selectedServiceId);

    }

    public CustomerDto findByCustomerId(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return customerBO.findById(selectedCustomerId);
    }

    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        // Convert BookingServiceDtos to BookingService entities
        ArrayList<BookingService> bookingServices = (ArrayList<BookingService>) bookingDto.toBookingServiceEntities();

        // Create Booking entity
        Booking booking = new Booking(
                bookingDto.getBookingId(),
                bookingDto.getCustomerId(),
                bookingDto.getCapacity(),
                bookingDto.getVenue(),
                bookingDto.getBookingDate(),
                bookingDto.getStatus(),
                bookingServices  // Use the converted list
        );

        // Get database connection
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Save booking details
            result = bookingDAO.save(booking);
            if (!result) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Save booking services
            boolean isBookingServicesSaved = bookingServiceDAO.saveBookingServiceList(booking.getBookingServices());
            if (!isBookingServicesSaved) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Commit transaction if everything is successful
            connection.commit();
            result = true;
        } catch (Exception e) {
            connection.rollback(); // Rollback in case of an exception
            throw new SQLException("Error saving booking: " + e.getMessage(), e);
        } finally {
            // Reset auto-commit mode
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        return result;
    }


    public boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.updateBookingStatusToUsed(bookingId);
    }


}
