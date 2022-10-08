package com.example.gestion_club_backend.payload;

import com.example.gestion_club_backend.Model.Compte;
import lombok.Data;

@Data
public class AuthResponse {
    JwtAuthenticationResponse jwtAuthenticationResponse;
    Compte compte;

    public AuthResponse(JwtAuthenticationResponse jwtAuthenticationResponse, Compte user) {
        this.compte=user;
        this.jwtAuthenticationResponse=jwtAuthenticationResponse;
    }
}
