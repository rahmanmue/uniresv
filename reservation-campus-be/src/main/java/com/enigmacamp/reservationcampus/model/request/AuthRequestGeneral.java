package com.enigmacamp.reservationcampus.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestGeneral {
    private Integer NIK;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
}
