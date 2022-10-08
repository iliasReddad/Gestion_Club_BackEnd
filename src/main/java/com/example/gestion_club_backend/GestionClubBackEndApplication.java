package com.example.gestion_club_backend;

import com.example.gestion_club_backend.security.JwtAuthenticationFilter;
import io.jsonwebtoken.Jwt;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication()

public class GestionClubBackEndApplication  {

    public static void main(String[] args)  {
        SpringApplication.run(GestionClubBackEndApplication.class, args);
    }
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
