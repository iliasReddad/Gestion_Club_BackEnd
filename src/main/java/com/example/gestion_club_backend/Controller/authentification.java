package com.example.gestion_club_backend.Controller;

import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Model.role.Role;
import com.example.gestion_club_backend.Model.role.RoleName;
import com.example.gestion_club_backend.Repository.CompteRepository;
import com.example.gestion_club_backend.Repository.RoleRepository;
import com.example.gestion_club_backend.exception.BlogapiException;
import com.example.gestion_club_backend.payload.*;
import com.example.gestion_club_backend.security.CurrentUser;
import com.example.gestion_club_backend.security.JwtTokenProvider;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class authentification {
    private static final String USER_ROLE_NOT_SET = "User role not set";

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository ;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest  ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        Compte compte = compteRepository.getUserByName(loginRequest.getUsernameOrEmail());
        Role role = compte.getRoles().stream().findFirst().orElse(null);
        return ResponseEntity.ok( new JwtAuthenticationResponse(jwt,role.getName().toString()) );
    }




        @PostMapping("/signup")
    public ResponseEntity<ApiResponse> RegisterUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(Boolean.TRUE.equals(compteRepository.existsByUsername(signUpRequest.getUsername()))){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Username is already taken");

        }
        if (Boolean.TRUE.equals(compteRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }


        String firstName = signUpRequest.getFirstName().toLowerCase();

        String lastName = signUpRequest.getLastName().toLowerCase();

        String username = signUpRequest.getUsername().toLowerCase();

        String email = signUpRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(signUpRequest.getPassword());
        Compte user = new Compte(firstName , lastName, username, email, password);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
        user.setRoles(roles);



        System.err.println(user);
        Compte result = compteRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(result.getId_Compte()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE,"User registered successfully"));

    }
}
