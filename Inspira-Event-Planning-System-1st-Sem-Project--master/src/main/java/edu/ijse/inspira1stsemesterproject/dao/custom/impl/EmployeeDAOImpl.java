package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.EmployeeDAO;
import edu.ijse.inspira1stsemesterproject.entity.Employee;
import edu.ijse.inspira1stsemesterproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last employee ID, e.g., "EM001"

            if (lastId != null && lastId.startsWith("EM")) { // Check if the format is correct
                String numericPart = lastId.substring(2); // Extract the numeric part, e.g., "001"

                try {
                    int idNumber = Integer.parseInt(numericPart); // Convert to integer
                    int newIdIndex = idNumber + 1; // Increment the number
                    return String.format("EM%03d", newIdIndex); // Format back to "EMnnn"
                } catch (NumberFormatException e) {
                    // Handle case where numeric part is invalid
                    System.out.println("Invalid numeric part in employee ID: " + numericPart);
                    return "EM001"; // Return default in case of format error
                }
            }
        }
        return "EM001"; // Return default ID if no records are found
    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from employee");

        ArrayList<Employee> employees = new ArrayList<>();

        while (rst.next()) {
            Employee entity = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8)

            );
            employees.add(entity);
        }
        return employees;
    }

    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into employee values(?,?,?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getJobPosition(),
                entity.getJoinDate(),
                entity.getSalary(),
                entity.getEmail(),
                entity.getBookingId()
        );
    }

    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update employee set  first_name = ?,  last_name = ?, position = ?, Join_date = ?, salary = ?, email = ?, booking_id  = ? where employee_id = ?",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getJobPosition(),
                entity.getJoinDate(),
                entity.getSalary(),
                entity.getEmail(),
                entity.getBookingId(),
                entity.getEmployeeId()
        );
    }

    @Override
    public Employee findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from employee where employee_id =?", employeeId);
    }
}
