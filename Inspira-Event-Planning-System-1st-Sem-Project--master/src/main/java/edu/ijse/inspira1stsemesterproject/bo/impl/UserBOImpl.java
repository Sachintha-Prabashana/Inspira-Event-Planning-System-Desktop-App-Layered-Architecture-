package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.dao.custom.UserDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.UserDAOImpl;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.entity.User;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean validateUser(String username, String password) throws SQLException, ClassNotFoundException {
       return userDAO.validate(username, password);
    }
    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
       return userDAO.getNextId();
    }
    @Override
    public boolean saveUser(UserDto user) throws SQLException, ClassNotFoundException {
       return userDAO.save(
               new User(user.getUserId(),user.getFirstName(),user.getLastName(),user.getUsername(),user.getEmail(),user.getPassword()
       ));
    }

    @Override
    public boolean deleteUser(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean isEmailExists(String text) throws SQLException, ClassNotFoundException {
        return userDAO.isEmailExists(text);
    }

    @Override
    public boolean updateUser(UserDto user) throws SQLException, ClassNotFoundException {
        return userDAO.update(
                new User(user.getUserId(),user.getFirstName(),user.getLastName(),user.getUsername(),user.getEmail(),user.getPassword()
                ));
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDto> userDtos = new ArrayList<>();
        ArrayList<User> usersList = userDAO.getAll();
        for (User user : usersList) {
            userDtos.add(
                    new UserDto
                            (user.getUserId(),user.getFirstName(),user.getLastName(),user.getUsername(),user.getEmail(),user.getPassword()
                            ));

        }
        return userDtos;
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException {
        return userDAO.getAllIds();
    }

    @Override
    public UserDto findById(String selectedUserId) throws SQLException, ClassNotFoundException {
        User user = userDAO.findById(selectedUserId);
        return new UserDto(
                user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword()
        );

    }
}
