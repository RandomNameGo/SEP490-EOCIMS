package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.LoginResponse;
import com.example.sep490_eocims.models.Staff;
import com.example.sep490_eocims.repositories.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@AllArgsConstructor
@RequestMapping
public class AuthServiceImpl implements AuthService {

    private StaffRepository staffRepository;

    private JwtService jwtService;

    public LoginResponse login(String email) {
        Optional<Staff> staff = staffRepository.findByEmail(email);
        LoginResponse loginResponse = new LoginResponse();
        if (staff.isPresent()) {
            loginResponse.setId(staff.get().getId());
            loginResponse.setFullName(staff.get().getFullName());
            loginResponse.setToken(jwtService.generateToken(staff.get()));
            return loginResponse;
        }
        return null;
    }
}
