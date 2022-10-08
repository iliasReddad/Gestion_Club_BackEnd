package com.example.gestion_club_backend.Service;


import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.ClubRequest;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ClubService {


    ResponseEntity<Club> AddClub(ClubRequest clubRequest, UserPrincipal currentUser);

    ResponseEntity<Club> getClub(Long id);

    PagedResponse<Club> getAllClubs(int page , int size );


    PagedResponse<Club> getAllClubsByUser(int page, int size, UserPrincipal userPrincipal);

    ResponseEntity<Club> UpdateClub(Long id , UserPrincipal currentUser, Club club );

    ResponseEntity<Club> UpdateClub(Long id, UserPrincipal currentUser, ClubRequest clubReq);

    ResponseEntity<ApiResponse> DeleteClub(Long id , UserPrincipal currentUser);

    ResponseEntity<ApiResponse> JoinClub(Long id, UserPrincipal currentUser);



    Boolean CheckJoinClub(Long id, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> LeaveClub(Long id, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> DeleteAllClub(UserPrincipal currentUser);


    PagedResponse<Club> getUserClubs(String username , int page, int size);




}
