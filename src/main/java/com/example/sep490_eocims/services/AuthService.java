package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(String email);
}
