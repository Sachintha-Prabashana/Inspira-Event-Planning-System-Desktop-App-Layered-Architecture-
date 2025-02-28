package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.*;
import edu.ijse.inspira1stsemesterproject.dao.DAOFactory;
import edu.ijse.inspira1stsemesterproject.dao.custom.EventDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.EventSupplierDAO;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompleteEventBOImpl implements CompleteEventBO {

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    CreateBookingBO createBookingBO = (CreateBookingBO) BOFactory.getInstance().getBO(BOFactory.BOType.CREATE_BOOKING);
    EventDAO eventDAO = (EventDAO) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EVENT);
    EventSupplierDAO eventSupplierDAO = (EventSupplierDAO) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EVENT_SUPPLIER);
    ItemBO itemBo = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

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
        return itemBo.findById(selectedItemId);

    }

    public SupplierDto findBySupplierId(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        return supplierBO.findById(selectedSupplierId);
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
