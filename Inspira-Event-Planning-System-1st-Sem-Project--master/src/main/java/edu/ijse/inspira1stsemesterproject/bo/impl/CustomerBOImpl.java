package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.CustomerBO;
import edu.ijse.inspira1stsemesterproject.dao.DAOFactory;
import edu.ijse.inspira1stsemesterproject.dao.custom.CustomerDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.CustomerDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.CUSTOMER);
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        ArrayList<Customer> customerList = customerDAO.getAll();
        for (Customer customer : customerList) {
            customerDtos.add(
                    new CustomerDto
                            (customer.getCustomerId(), customer.getCustomerTitle(), customer.getFirstName(), customer.getLastName(), customer.getNic(), customer.getEmail(), customer.getRegistrationDate()
                    ));

        }
        return customerDtos;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(
                new Customer
                        (customerDto.getCustomerId(),customerDto.getCustomerTitle(),customerDto.getFirstName(),customerDto.getLastName(),customerDto.getNic(),customerDto.getEmail(),customerDto.getRegistrationDate()
                ));
    }

    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(
                new Customer
                        (customerDto.getCustomerId(),customerDto.getCustomerTitle(),customerDto.getFirstName(),customerDto.getLastName(),customerDto.getNic(),customerDto.getEmail(),customerDto.getRegistrationDate()
                        ));
    }

    public CustomerDto findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCustomerId);
//
//        if (rst.next()) {
//            return new CustomerDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getString(6),
//                    rst.getDate(7)
//            );
//        }
//        return null;

        Customer customer = customerDAO.findById(selectedCustomerId);
        return new CustomerDto
                (customer.getCustomerId(),customer.getCustomerTitle(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getEmail(),customer.getRegistrationDate()
                );
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }
}
