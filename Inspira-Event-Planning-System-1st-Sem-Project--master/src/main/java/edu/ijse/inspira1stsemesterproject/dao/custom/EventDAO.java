package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.Event;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EventDAO extends CrudDAO<Event> {
    boolean isVenueAvailable(String venue, java.sql.Date date) throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException ;
    boolean save(Event entity) throws SQLException, ClassNotFoundException;

}
