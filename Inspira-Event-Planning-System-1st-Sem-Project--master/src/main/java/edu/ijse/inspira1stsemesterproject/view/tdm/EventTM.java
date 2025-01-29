package edu.ijse.inspira1stsemesterproject.view.tdm;
import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventTM {
    private String eventId;
    private String bookingId;
    private String itemId;
    private String eventType;
    private String eventName;
    private double budget;
    private String venue;
    private Date date;
    private String supplierId;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private Button action;


}
