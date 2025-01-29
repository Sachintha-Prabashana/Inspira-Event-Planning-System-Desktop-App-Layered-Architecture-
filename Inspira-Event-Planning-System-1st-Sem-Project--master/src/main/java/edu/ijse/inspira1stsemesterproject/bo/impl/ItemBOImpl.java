package edu.ijse.inspira1stsemesterproject.bo.impl;

import edu.ijse.inspira1stsemesterproject.bo.ItemBO;
import edu.ijse.inspira1stsemesterproject.dao.custom.ItemDAO;
import edu.ijse.inspira1stsemesterproject.dao.custom.impl.ItemDAOImpl;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.entity.Customer;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;
import edu.ijse.inspira1stsemesterproject.entity.Item;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = new ItemDAOImpl();
    public String getNextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextId();
    }

    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        ArrayList<Item> itemsList = itemDAO.getAll();
        for (Item item : itemsList) {
            itemDtos.add(
                    new ItemDto(
                          item.getItemId(),
                          item.getItemName(),
                          item.getItemDescription(),
                          item.getItemPrice(),
                          item.getItemQuantity(),
                          item.getSupplierId()

                    ));
        }
        return itemDtos;
    }


    public ItemDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.findById(selectedItemId);
        return new ItemDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDescription(),
                item.getItemPrice(),
                item.getItemQuantity(),
                item.getSupplierId()

        );

    }


    public ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemIds(supplierId);
    }

    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId);
    }

    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(
                new Item(
                        itemDto.getItemId(),
                        itemDto.getItemName(),
                        itemDto.getItemDescription(),
                        itemDto.getItemPrice(),
                        itemDto.getItemQuantity(),
                        itemDto.getSupplierId()
                ));

    }

    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(
                new Item(
                        itemDto.getItemId(),
                        itemDto.getItemName(),
                        itemDto.getItemDescription(),
                        itemDto.getItemPrice(),
                        itemDto.getItemQuantity(),
                        itemDto.getSupplierId()
                ));
    }


}
