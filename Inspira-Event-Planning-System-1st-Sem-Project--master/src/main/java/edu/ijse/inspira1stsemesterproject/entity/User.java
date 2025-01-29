package edu.ijse.inspira1stsemesterproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
