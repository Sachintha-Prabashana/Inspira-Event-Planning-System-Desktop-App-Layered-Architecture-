package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.CustomerDAO;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "C001";
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<Customer> customers = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
            customers.add(customer);
        }
        return customers;
    }

    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into customer values(?,?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getRegistrationDate()
        );
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }

    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update customer set  cust_title = ?, first_name = ?, last_name = ?, nic = ?, email = ?, registration_date = ? where customer_id = ?",
                entity.getCustomerTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getRegistrationDate(),
                entity.getCustomerId()
        );
    }

    public Customer findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCustomerId);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
        }
        return null;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
}
