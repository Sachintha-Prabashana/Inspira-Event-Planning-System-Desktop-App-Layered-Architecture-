package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.entity.Supplier;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO {
    String getNextSupplierId() throws SQLException, ClassNotFoundException ;
    ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException ;
    SupplierDto findById(String selectedSupplierId) throws SQLException, ClassNotFoundException ;
    boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException ;
    boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException ;
    boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException ;
}
