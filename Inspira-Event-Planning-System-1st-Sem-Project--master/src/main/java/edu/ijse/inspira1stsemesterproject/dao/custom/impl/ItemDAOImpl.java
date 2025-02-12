package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.ItemDAO;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;
import edu.ijse.inspira1stsemesterproject.entity.Item;
import edu.ijse.inspira1stsemesterproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select item_id from item order by item_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("I%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "I001";
    }

    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item");

        ArrayList<Item> itemsList = new ArrayList<>();

        while (rst.next()) {
            Item entity = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)

            );
            itemsList.add(entity);
        }
        return itemsList;
    }


    public Item findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        String query = "SELECT item_id FROM item WHERE supplier_id = ?";

        ResultSet rst = SQLUtil.execute(query, supplierId);

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }

    public boolean reduceQty(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException {
        if (eventSupplier.getItemQuantity() <= 0) {
            System.err.println("Invalid quantity to reduce: " + eventSupplier.getItemQuantity());
            return false;
        }

        // Ensure that the item ID is not null or empty
        if (eventSupplier.getItemId() == null || eventSupplier.getItemId().isEmpty()) {
            System.err.println("Invalid item ID: " + eventSupplier.getItemId());
            return false;
        }

        // Update the item quantity in the database
        return SQLUtil.execute(
                "UPDATE item SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?",
                eventSupplier.getItemQuantity(),  // Quantity to reduce
                eventSupplier.getItemId(),       // Item ID to identify the item
                eventSupplier.getItemQuantity()  // Ensure enough quantity is available
        );
    }


    public boolean delete(String itemId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from item where item_id=?", itemId);
    }

    public boolean save(Item itemDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into item values(?,?,?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getItemPrice(),
                itemDto.getItemQuantity(),
                itemDto.getSupplierId()


        );
    }

    public boolean update(Item itemDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update item set  item_name = ?, description = ? , cost = ?, quantity = ?, supplier_id = ? where item_id = ?",
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getItemPrice(),
                itemDto.getItemQuantity(),
                itemDto.getSupplierId(),
                itemDto.getItemId()
        );
    }


}
