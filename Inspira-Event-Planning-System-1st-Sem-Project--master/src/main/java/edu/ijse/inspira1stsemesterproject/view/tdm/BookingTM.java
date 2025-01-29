package edu.ijse.inspira1stsemesterproject.view.tdm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingTM {
    private String bookingId;
    private String customerId;
    private String serviceId;
    private int capacity;
    private String venue;
    private Date bookingDate;
    private Button action;
}
