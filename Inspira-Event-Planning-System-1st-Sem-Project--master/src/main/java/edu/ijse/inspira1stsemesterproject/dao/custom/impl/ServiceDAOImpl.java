package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.ServiceDAO;
import edu.ijse.inspira1stsemesterproject.entity.Service;
import edu.ijse.inspira1stsemesterproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAOImpl implements ServiceDAO {
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select service_id from service");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }

    public Service findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from service where service_id=?", selectedServiceId);

        if (rst.next()) {
            return new Service(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT service_id FROM service ORDER BY service_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last employee ID, e.g., "EM001"

            if (lastId != null && lastId.startsWith("SV")) { // Check if the format is correct
                String numericPart = lastId.substring(2); // Extract the numeric part, e.g., "001"

                try {
                    int idNumber = Integer.parseInt(numericPart); // Convert to integer
                    int newIdIndex = idNumber + 1; // Increment the number
                    return String.format("SV%03d", newIdIndex); // Format back to "EMnnn"
                } catch (NumberFormatException e) {
                    // Handle case where numeric part is invalid
                    System.out.println("Invalid numeric part in employee ID: " + numericPart);
                    return "SV001"; // Return default in case of format error
                }
            }
        }
        return "SV001";
    }

    public ArrayList<Service> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from service");

        ArrayList<Service> services = new ArrayList<>();

        while (rst.next()) {
            Service entity = new Service(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
            services.add(entity);
        }
        return services;
    }

    public boolean save(Service entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into service values(?,?,?)",
                entity.getServiceId(),
                entity.getPrice(),
                entity.getServiceType()

        );
    }

    public boolean update(Service entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update service set  price = ?, service_type = ? where service_id = ?",
                entity.getPrice(),
                entity.getServiceType(),
                entity.getServiceId()
        );
    }

    public boolean delete(String serviceId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from service where service_id=?", serviceId);
    }
}
