package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.request.AuthRequestAdmin;
import com.enigmacamp.reservationcampus.model.request.AuthRequestGeneral;
import com.enigmacamp.reservationcampus.model.request.AuthRequestLogin;
import com.enigmacamp.reservationcampus.model.response.LoginResponse;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerAdmin(AuthRequestAdmin authRequestAdmin);

    RegisterResponse registerGeneral(AuthRequestGeneral authRequestGeneral);

    LoginResponse login(AuthRequestLogin authRequestLogin);

}

