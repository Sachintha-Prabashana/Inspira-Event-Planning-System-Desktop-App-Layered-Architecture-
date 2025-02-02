package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.EventDAO;
import edu.ijse.inspira1stsemesterproject.entity.Event;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAOImpl implements EventDAO {

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select event_id from event order by event_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("E%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "E001";
    }

    @Override
    public ArrayList<Event> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    public boolean save(Event entity) throws SQLException, ClassNotFoundException {
        boolean isEventSaved = CrudUtil.execute(
                "INSERT INTO event (event_id, booking_id, event_type, event_name, budget, venue, event_date) VALUES (?,?,?,?,?,?,?)",
                entity.getEventId(),
                entity.getBookingId(),
                entity.getEventType(),
                entity.getEventName(),
                entity.getBudget(),
                entity.getVenue(),
                entity.getDate()
        );
        return isEventSaved;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Event entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Event findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean isVenueAvailable(String venue, Date date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM Event WHERE venue = ? AND event_date = ?",
                venue, date
        );
        if (resultSet.next()) {
            return resultSet.getInt(1) == 0; // Venue is available if count is 0
        }
        return false;
    }
}
