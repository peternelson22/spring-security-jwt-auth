package com.nelson.demo.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    @Email
    @Length(min = 5, max = 70)
    private String email;

    @NotNull
    @Length(min = 5, max = 64)
    private String password;
}
