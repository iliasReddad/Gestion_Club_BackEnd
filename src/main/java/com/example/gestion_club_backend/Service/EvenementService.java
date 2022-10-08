package com.example.gestion_club_backend.Service;

import com.example.gestion_club_backend.Model.Evenement;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface EvenementService {

    PagedResponse<Evenement> getAllEvents(int page , int size );
    ResponseEntity<Evenement> getEvent(Long id);
    PagedResponse<Evenement> getEventsByClubs(int page, int size,Long id);
    ResponseEntity<Evenement> CreateEvent (Evenement evenement , UserPrincipal userPrincipal);
    ResponseEntity<Evenement> UpdateEvent (Long id , Evenement evenement , UserPrincipal userPrincipal);
    ResponseEntity<ApiResponse> DeleteEvent (Long id , UserPrincipal userPrincipal);


}
