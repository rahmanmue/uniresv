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
public class AuthRequestStudent {
    private Integer nim;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String photo;
    private Date birthDate;
}
