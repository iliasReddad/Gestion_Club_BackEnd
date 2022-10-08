package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.naming.ldap.PagedResultsResponseControl;
import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {


   Page<Club> findAllByMembresIsContaining(Compte user, Pageable pageable);

   List<Club> findAllByMembresIsContaining(Compte user);

}