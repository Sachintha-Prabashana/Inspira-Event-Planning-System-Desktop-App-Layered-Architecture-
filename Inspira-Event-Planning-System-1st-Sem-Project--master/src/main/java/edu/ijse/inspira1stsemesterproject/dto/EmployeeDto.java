package edu.ijse.inspira1stsemesterproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String jobPosition;
    private Date joinDate;
    private Double salary;
    private String email;
    private String bookingId;
}
