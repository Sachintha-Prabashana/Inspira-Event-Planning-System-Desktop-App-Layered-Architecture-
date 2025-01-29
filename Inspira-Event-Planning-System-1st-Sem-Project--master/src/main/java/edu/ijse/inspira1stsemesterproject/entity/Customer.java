package edu.ijse.inspira1stsemesterproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    private String customerId;
    private String customerTitle;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private Date registrationDate;

    public String concatName(){
        return firstName + " " + lastName;
    }
}
