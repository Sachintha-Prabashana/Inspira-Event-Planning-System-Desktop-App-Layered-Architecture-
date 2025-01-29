package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO {
    String getNextCustomerId() throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException ;
    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException ;
    boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException ;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException ;
    CustomerDto findById(String selectedCustomerId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException ;
}
