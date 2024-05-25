package com.enigmacamp.reservationcampus.model.response;

import com.enigmacamp.reservationcampus.utils.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String email;
    private ERole role;
}