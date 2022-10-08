package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Evenement;
import com.example.gestion_club_backend.Model.Reunion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {

    Page<Reunion> findAllByClubIsContaining(Club club, Pageable pageable);

}