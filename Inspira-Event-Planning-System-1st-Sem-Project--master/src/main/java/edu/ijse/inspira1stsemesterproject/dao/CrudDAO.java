package edu.ijse.inspira1stsemesterproject.dao;

import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> {
    String getNextId() throws SQLException, ClassNotFoundException;
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;
    boolean save(T entity) throws SQLException, ClassNotFoundException ;
    boolean delete(String customerId) throws SQLException, ClassNotFoundException ;
    boolean update(T entity) throws SQLException, ClassNotFoundException ;
    T findById(String selectedCustomerId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException ;
}
