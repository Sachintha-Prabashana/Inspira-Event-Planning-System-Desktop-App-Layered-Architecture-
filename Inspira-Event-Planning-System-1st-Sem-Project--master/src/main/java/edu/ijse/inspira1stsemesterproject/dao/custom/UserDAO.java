package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO <User> {
    public boolean validate(String username, String password) throws SQLException, ClassNotFoundException ;
    public boolean isEmailExists(String text) throws SQLException, ClassNotFoundException ;
}
