package edu.ijse.inspira1stsemesterproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EventSupplier {
    private String eventId;
    private String supplierId;
    private String itemId;
    private int itemQuantity;
    private double totalPrice;


}
