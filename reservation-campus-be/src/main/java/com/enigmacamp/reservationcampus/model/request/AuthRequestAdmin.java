package com.enigmacamp.reservationcampus.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestAdmin {

    private String email;
    private String password;
//    private String fullName;
//    private String phoneNumber;
//    private String photo;

}
