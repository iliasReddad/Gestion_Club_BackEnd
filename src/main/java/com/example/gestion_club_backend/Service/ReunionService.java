package com.example.gestion_club_backend.Service;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Reunion;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ReunionService {

       ResponseEntity<Reunion> AddReunion(Reunion reunion , UserPrincipal currentUser);
       ResponseEntity<Reunion> getReunion(Long id);
       PagedResponse<Reunion> getAllReunion(int page , int size);
       PagedResponse<Reunion> getAllReunionByUser(int page , int size,UserPrincipal currentUser);
       PagedResponse<Reunion> getAllReunionByClubs(int page , int size,Long id);
       ResponseEntity<ApiResponse>  DeleteReunion(Long id, UserPrincipal userPrincipal);

}
