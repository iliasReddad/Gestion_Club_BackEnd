package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {
}