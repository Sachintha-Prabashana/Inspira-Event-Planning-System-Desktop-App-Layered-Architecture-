package edu.ijse.inspira1stsemesterproject.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
@AllArgsConstructor

public class ItemDto {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemQuantity;
    private String supplierId;
}
