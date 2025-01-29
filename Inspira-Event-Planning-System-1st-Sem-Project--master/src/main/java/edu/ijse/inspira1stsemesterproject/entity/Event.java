package edu.ijse.inspira1stsemesterproject.entity;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Event {
    private String eventId;
    private String bookingId;
    private String eventType;
    private String eventName;
    private Double budget;
    private String venue;
    private Date date;

    private ArrayList<EventSupplier> eventSuppliersList;
}
