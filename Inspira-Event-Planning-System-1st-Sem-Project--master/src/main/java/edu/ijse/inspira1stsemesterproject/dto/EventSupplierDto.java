package edu.ijse.inspira1stsemesterproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EventSupplierDto {
    private String eventId;
    private String supplierId;
    private String itemId;
    private int itemQuantity;
    private double totalPrice;


}
