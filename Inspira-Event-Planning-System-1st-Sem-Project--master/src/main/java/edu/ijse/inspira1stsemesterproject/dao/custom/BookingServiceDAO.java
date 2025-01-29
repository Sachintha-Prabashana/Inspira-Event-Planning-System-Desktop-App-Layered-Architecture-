package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dto.BookingServiceDto;
import edu.ijse.inspira1stsemesterproject.entity.BookingService;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingServiceDAO {
    boolean saveBookingServiceList(ArrayList<BookingService> bookingServices) throws SQLException, ClassNotFoundException ;

    boolean saveBookingServiceDetails(BookingService bookingServiceDto) throws SQLException, ClassNotFoundException ;
}
