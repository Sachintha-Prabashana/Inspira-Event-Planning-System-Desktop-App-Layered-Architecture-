package edu.ijse.inspira1stsemesterproject.dao.custom;


import edu.ijse.inspira1stsemesterproject.dao.CrudDAO;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EventSupplierDAO extends CrudDAO<EventSupplier> {
    boolean saveEventSuppliersList(ArrayList<EventSupplier> eventSuppliersList) throws SQLException, ClassNotFoundException ;
    boolean saveEventSuppliers(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException ;
}
