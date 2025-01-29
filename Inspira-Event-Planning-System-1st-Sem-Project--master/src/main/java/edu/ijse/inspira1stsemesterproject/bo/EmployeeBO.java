package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO {
    String getNextEmployeeId() throws SQLException, ClassNotFoundException ;
    ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException ;
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateCustomer(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException ;
    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException ;
}
