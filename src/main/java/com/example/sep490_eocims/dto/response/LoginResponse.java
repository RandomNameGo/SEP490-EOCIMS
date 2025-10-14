package com.example.sep490_eocims.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private long id;
    private String fullName;
    private String token;
}
