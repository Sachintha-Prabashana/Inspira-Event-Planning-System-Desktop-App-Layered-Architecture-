package edu.ijse.inspira1stsemesterproject.dao.custom;

import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;
import edu.ijse.inspira1stsemesterproject.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException ;
    boolean reduceQty(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException;
}
