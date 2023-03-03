package com.depandre.expenseTrackerAPI.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email(message = "Please provide valid email")
    private String email;

    @NotNull(message = "Please enter a password")
    @Size(min = 5, message = "Password should be atleast 5 character")
    private String password;
    private Long age = 0L;

}
