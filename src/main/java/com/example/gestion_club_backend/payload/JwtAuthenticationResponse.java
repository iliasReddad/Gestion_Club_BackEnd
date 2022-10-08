package com.example.gestion_club_backend.payload;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private String roles;



	public JwtAuthenticationResponse(String accessToken  ,String roles) {
		this.roles=roles;
		this.accessToken = accessToken;
	}

}
