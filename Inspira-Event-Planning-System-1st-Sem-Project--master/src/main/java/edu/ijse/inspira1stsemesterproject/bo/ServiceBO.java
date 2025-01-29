package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceBO {
    ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException ;
    ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException ;
    String getNextServiceId() throws SQLException, ClassNotFoundException ;
    ArrayList<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException ;
    boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException ;
    boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException ;
    boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException ;
}
