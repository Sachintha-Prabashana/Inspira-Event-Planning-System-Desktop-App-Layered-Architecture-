package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO{
    boolean validateUser(String username, String password) throws SQLException, ClassNotFoundException ;
    String getNextUserId() throws SQLException, ClassNotFoundException ;
    boolean saveUser(UserDto user) throws SQLException, ClassNotFoundException ;
    boolean deleteUser(String customerId) throws SQLException, ClassNotFoundException ;
    boolean isEmailExists(String text) throws SQLException, ClassNotFoundException ;
    boolean updateUser(UserDto user) throws SQLException, ClassNotFoundException ;
    ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException ;
    UserDto findById(String selectedUserId) throws SQLException, ClassNotFoundException ;
}
