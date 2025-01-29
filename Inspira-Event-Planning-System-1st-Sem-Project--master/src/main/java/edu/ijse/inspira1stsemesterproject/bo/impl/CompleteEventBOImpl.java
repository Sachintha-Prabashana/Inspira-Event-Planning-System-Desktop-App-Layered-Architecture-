package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.CompleteEventBO;
import edu.ijse.inspira1stsemesterproject.bo.CreateBookingBO;
import edu.ijse.inspira1stsemesterproject.bo.ItemBO;
import edu.ijse.inspira1stsemesterproject.bo.SupplierBO;
import edu.ijse.inspira1stsemesterproject.dao.custom.EventDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.EventSupplierDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.ItemDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.SupplierDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.EventDAOImpl;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.EventSupplierDAOImpl;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.ItemDAOImpl;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.SupplierDAOImpl;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.entity.*;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompleteEventBOImpl implements CompleteEventBO {

    SupplierBO supplierBO = new SupplierBOImpl();
    CreateBookingBO createBookingBO = new CreateBookingBOImpl();
    EventDAO eventDAO = new EventDAOImpl();
    EventSupplierDAO eventSupplierDAO = new EventSupplierDAOImpl();
    ItemDAO itemDAO = new ItemDAOImpl();
    ItemBO itemBo = new ItemBOImpl();
    SupplierDAO supplierDAO = new SupplierDAOImpl();

    public ArrayList<String> loadSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierBO.getAllSupplierIds();

    }

    public String getNextEventId() throws SQLException, ClassNotFoundException {
        return eventDAO.getNextId();
    }

    public ArrayList<String> loadBookingIds() throws SQLException, ClassNotFoundException {
        return createBookingBO.loadAllBookingIds();
    }

    //combo box ekn find krnn
    public ItemDto findByItemId(String selectedItemId) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.findById(selectedItemId);
        return new ItemDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDescription(),
                item.getItemPrice(),
                item.getItemQuantity(),
                item.getSupplierId()
        );

    }

    public SupplierDto findBySupplierId(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.findById(selectedSupplierId);
        return new SupplierDto(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getEmail()
        );
    }

    public ArrayList<String> loadItemIDs(String itemId) throws SQLException, ClassNotFoundException {
        return itemBo.getAllItemIds(itemId);
    }

    @Override
    public boolean completeEventCreation(EventDto eventDto) throws SQLException, ClassNotFoundException {
        ArrayList<EventSupplier> eventSuppliers = (ArrayList<EventSupplier>) eventDto.toEventSupplierEntities();

        Event event = new Event(
                eventDto.getEventId(),
                eventDto.getBookingId(),
                eventDto.getEventType(),
                eventDto.getEventName(),
                eventDto.getBudget(),
                eventDto.getVenue(),
                eventDto.getDate(),
                eventSuppliers  // Use the converted list
        );

        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Save booking details
            result = eventDAO.save(event);
            if (!result) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Save booking services
            boolean isSaved = eventSupplierDAO.saveEventSuppliersList(event.getEventSuppliersList());
            if (!isSaved) {
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
       return createBookingBO.updateBookingStatusToUsed(bookingId);
    }

    public boolean isVenueAvailable(String venue, java.sql.Date date) throws SQLException, ClassNotFoundException {
       return eventDAO.isVenueAvailable(venue, date);
    }
}
