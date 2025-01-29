package edu.ijse.inspira1stsemesterproject.dto;
import edu.ijse.inspira1stsemesterproject.entity.EventSupplier;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventDto {
    private String eventId;
    private String bookingId;
    private String eventType;
    private String eventName;
    private Double budget;
    private String venue;
    private Date date;

    private ArrayList<EventSupplierDto> eventSupplierDtos;

    public List<EventSupplier> toEventSupplierEntities() {
        if (this.eventSupplierDtos == null) {
            return new ArrayList<>(); // Return an empty list if null
        }

        return this.eventSupplierDtos.stream()
                .map(dto -> new EventSupplier(
                        this.eventId,       // Assign the event ID from the main DTO
                        dto.getSupplierId(), // Get the supplier ID from EventSupplierDto
                        dto.getItemId(),
                        dto.getItemQuantity(),    // Get the item quantity from EventSupplierDto
                        dto.getTotalPrice()       // Get the price from EventSupplierDto
                ))
                .collect(Collectors.toList());
    }

}
