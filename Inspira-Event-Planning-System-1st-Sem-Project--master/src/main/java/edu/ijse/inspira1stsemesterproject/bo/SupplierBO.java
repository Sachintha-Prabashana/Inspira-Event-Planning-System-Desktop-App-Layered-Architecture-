package edu.ijse.inspira1stsemesterproject.bo;

import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO{
    String getNextSupplierId() throws SQLException, ClassNotFoundException ;
    ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException ;
    SupplierDto findById(String selectedSupplierId) throws SQLException, ClassNotFoundException ;
    boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException ;
    boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException ;
    boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException ;
}
