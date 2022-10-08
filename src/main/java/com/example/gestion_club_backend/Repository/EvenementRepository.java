package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Model.Evenement;
import com.example.gestion_club_backend.payload.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    Page<Evenement> findAllByClubAssociésIsContaining(Club club, Pageable pageable);



}