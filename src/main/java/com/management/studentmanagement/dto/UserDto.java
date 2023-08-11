package com.management.studentmanagement.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "this field can't be empty")
    private String fname;
    @NotEmpty(message = "this field can't be empty")
    private String lname;
    @NotEmpty(message = "this field can't be empty")
    @Email
    private String email;
    @NotEmpty(message = "this field can't be empty")
    private String password;
}
