package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.BookingService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingServiceDAO extends CrudDAO<BookingService> {
    boolean saveBookingServiceList(ArrayList<BookingService> bookingServices) throws SQLException, ClassNotFoundException ;

    boolean saveBookingServiceDetails(BookingService bookingServiceDto) throws SQLException, ClassNotFoundException ;
}
