package com.example.sep490_eocims.services;

import com.example.sep490_eocims.models.Staff;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.StringJoiner;

@Service
public class JwtService {

    private SecretKey getSecretKey() {
        String secret = "aec5162f0ed647d4bb3cc9c926b2fb6af809992b56055bed2130adb9a1c3de8da55bfd2ef287029cb7a7e36b0d15b14b04d68cde8b060c3e5fbc6fcc7891bbe9";
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    }

    public String generateToken(Staff staff) {
        try {
            // Header
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512)
                    .type(JOSEObjectType.JWT)
                    .build();

            // Payload (claim)
            Date now = new Date();
            long validityInMs = 3600_000;
            Date exp = new Date(now.getTime() + validityInMs);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(staff.getEmail())
                    .issuer("eocims.com")
                    .issueTime(now)
                    .expirationTime(exp)
                    .claim("scope", staff.getRole())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            JWSSigner signer = new MACSigner(getSecretKey());
            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException("Cannot generate JWT", e);
        }
    }

}
