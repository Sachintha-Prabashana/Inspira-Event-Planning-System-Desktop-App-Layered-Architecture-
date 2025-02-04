package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.EmployeeBO;
import edu.ijse.inspira1stsemesterproject.dao.DAOFactory;
import edu.ijse.inspira1stsemesterproject.dao.custom.EmployeeDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.EmployeeDAOImpl;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.ServiceDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.entity.Employee;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EMPLOYEE);

    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
            ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
            ArrayList<Employee> employeeList = employeeDAO.getAll();
            for (Employee employee : employeeList) {
                employeeDtos.add(
                        new EmployeeDto(
                                employee.getEmployeeId(),
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getJobPosition(),
                                employee.getJoinDate(),
                                employee.getSalary(),
                                employee.getEmail(),
                                employee.getBookingId()
                        ));



            }
            return employeeDtos;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(
                new Employee(
                        dto.getEmployeeId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getJobPosition(),
                        dto.getJoinDate(),
                        dto.getSalary(),
                        dto.getEmail(),
                        dto.getBookingId()
                ));

    }

    public boolean updateCustomer(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(
                new Employee(
                        employeeDto.getEmployeeId(),
                        employeeDto.getFirstName(),
                        employeeDto.getLastName(),
                        employeeDto.getJobPosition(),
                        employeeDto.getJoinDate(),
                        employeeDto.getSalary(),
                        employeeDto.getEmail(),
                        employeeDto.getBookingId()
                ));
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }
}
