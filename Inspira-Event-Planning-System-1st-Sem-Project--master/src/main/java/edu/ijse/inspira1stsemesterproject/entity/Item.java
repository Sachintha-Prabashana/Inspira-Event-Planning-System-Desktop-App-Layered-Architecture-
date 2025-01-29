package edu.ijse.inspira1stsemesterproject.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
@AllArgsConstructor

public class Item {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemQuantity;
    private String supplierId;
}
