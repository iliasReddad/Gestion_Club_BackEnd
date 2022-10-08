package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.exception.ResourceNotFoundException;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByUsername(@NotBlank String username);

    Optional<Compte> findByEmail(@NotBlank String email);

    Boolean existsByUsername(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    Optional<Compte> findByUsernameOrEmail(String username, String email);

    default Compte getUser(UserPrincipal currentUser) {
        return getUserByName(currentUser.getUsername());
    }

    default Compte getUserByName(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}