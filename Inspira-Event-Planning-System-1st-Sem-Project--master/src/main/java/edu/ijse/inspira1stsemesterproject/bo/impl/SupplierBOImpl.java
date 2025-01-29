package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.SupplierBO;
import edu.ijse.inspira1stsemesterproject.dao.custom.SupplierDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.SupplierDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.entity.Supplier;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = new SupplierDAOImpl();
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextId();
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while (rst.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public SupplierDto findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

        if (rst.next()) {
            return new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;

    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(
                new Supplier
                        (supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getEmail()
                        ));
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(
                new Supplier
                        (supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getEmail()
                        ));
    }

    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }
}
