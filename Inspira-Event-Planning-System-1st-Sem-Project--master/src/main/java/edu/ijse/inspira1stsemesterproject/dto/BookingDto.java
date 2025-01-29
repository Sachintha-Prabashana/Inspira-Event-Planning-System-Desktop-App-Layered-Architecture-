package edu.ijse.inspira1stsemesterproject.dto;


import edu.ijse.inspira1stsemesterproject.entity.BookingService;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String bookingId;
    private String customerId;
    private int capacity;
    private String venue;
    private Date bookingDate;
    private String status;

    public ArrayList<BookingServiceDto> bookingServiceDtos;

    public List<BookingService> toBookingServiceEntities() {
        if (this.bookingServiceDtos == null) {
            return new ArrayList<>(); // Return an empty list if null
        }

        return this.bookingServiceDtos.stream()
                .map(dto -> new BookingService(
                        this.bookingId, // Assign the booking ID from BookingDto
                        dto.getServiceId(), // Get the service ID from BookingServiceDto
                        this.bookingDate
                ))
                .collect(Collectors.toList());
    }
}
