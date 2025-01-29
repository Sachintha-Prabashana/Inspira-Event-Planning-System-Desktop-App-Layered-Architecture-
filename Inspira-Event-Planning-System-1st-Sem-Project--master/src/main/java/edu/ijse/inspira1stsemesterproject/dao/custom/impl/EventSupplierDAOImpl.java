package edu.ijse.inspira1stsemesterproject.dao.custom.impl;

import edu.ijse.inspira1stsemesterproject.dao.custom.EventSupplierDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.ItemDAO;
import edu.ijse.inspira1stsemesterproject.dto.EventSupplierDto;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventSupplierDAOImpl implements EventSupplierDAO {
    ItemDAO itemDAO = new ItemDAOImpl();
    public boolean saveEventSuppliersList(ArrayList<EventSupplier> eventSuppliersList) throws SQLException, ClassNotFoundException {

        for (EventSupplier eventSupplier : eventSuppliersList) {
            boolean isEventSupplierSaved = saveEventSuppliers(eventSupplier);
            if (!isEventSupplierSaved) {
                return false;
            }

            boolean isItemUpdated = itemDAO.reduceQty(eventSupplier);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    public boolean saveEventSuppliers(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO EventSupplier (event_id, supplier_id, itemQty, price) VALUES (?,?,?,?)",
                eventSupplier.getEventId(),
                eventSupplier.getSupplierId(),
                eventSupplier.getItemQuantity(),
                eventSupplier.getTotalPrice()
        );
    }
}
