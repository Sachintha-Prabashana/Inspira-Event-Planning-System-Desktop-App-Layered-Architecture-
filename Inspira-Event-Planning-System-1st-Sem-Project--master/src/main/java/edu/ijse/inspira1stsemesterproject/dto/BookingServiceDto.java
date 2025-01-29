package edu.ijse.inspira1stsemesterproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookingServiceDto {
    private String bookingId;
    private String serviceId;
    private Date bookingDate;
}
