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
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return true;
        }
        return false;
    }
    public String getNextId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select user_id from user order by user_id desc limit 1";

        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String string = rs.getString(1);
            String subString = string.substring(1);
            int lastIdIndex = Integer.parseInt(subString);
            int nextIdIndex = lastIdIndex + 1;

            String nextUserId = String.format("U%03d", nextIdIndex);
            return nextUserId;
        }
        return "U001";
    }
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into user values (?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, entity.getUserId());
        pst.setString(2, entity.getFirstName());
        pst.setString(3, entity.getLastName());
        pst.setString(4, entity.getUsername());
        pst.setString(5, entity.getEmail());
        pst.setString(6, entity.getPassword());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean isEmailExists(String text) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select email from user where email = ?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, text);

        ResultSet rs = pst.executeQuery();

        return rs.next();
    }

    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update user set first_name = ?, last_name = ?, username = ?, email = ?, password = ? where user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, entity.getUsername());
        preparedStatement.setString(4, entity.getEmail());
        preparedStatement.setString(5, entity.getPassword());
        preparedStatement.setString(6, entity.getUserId());

        return preparedStatement.executeUpdate()>0;
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
