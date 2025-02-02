package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.UserDAO;
import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import edu.ijse.inspira1stsemesterproject.entity.User;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    public boolean validate(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM user WHERE username = ? AND password = ?", username, password);
        return rst.next();
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // Last user ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("U%03d", newIdIndex); // Return the new user ID in format Unnn
        }
        return "U001";
    }

    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO user VALUES (?,?,?,?,?,?)", entity.getUserId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT email FROM user WHERE email = ?", email);
        return rs.next();
    }

    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE user SET first_name = ?, last_name = ?, username = ?, email = ?, password = ? WHERE user_id = ?", entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getUserId());
    }

    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from user");

        ArrayList<User> users = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            users.add(user);
        }
        return users;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select user_id from user");

        ArrayList<String> user_ids = new ArrayList<>();

        while (rst.next()) {
            user_ids.add(rst.getString(1));
        }

        return user_ids;
    }

    public User findById(String selectedUserId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from user where user_id=?", selectedUserId);

        if (rst.next()) {
            return new User(
                    rst.getString(1),  // user ID
                    rst.getString(2),  // fName
                    rst.getString(3),  // lname
                    rst.getString(4), //uname
                    rst.getString(5), //email
                    rst.getString(6) //password
            );
        }
        return null;
    }
}
