package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.SupplierDAO;
import edu.ijse.inspira1stsemesterproject.entity.Supplier;
import edu.ijse.inspira1stsemesterproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "S001";
    }

    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from supplier");

        ArrayList<Supplier> suppliers = new ArrayList<>();

        while (rst.next()) {
            Supplier entity = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            suppliers.add(entity);
        }
        return suppliers;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public Supplier findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;

    }

    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into supplier values(?,?,?)",
                entity.getSupplierId(),
                entity.getSupplierName(),
                entity.getEmail()

        );
    }

    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update supplier set  supplier_name = ?, email = ? where supplier_id = ?",
                entity.getSupplierName(),
                entity.getEmail(),
                entity.getSupplierId()
        );
    }

    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }
}
