package com.example.gestion_club_backend.Service.Impl;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Reunion;
import com.example.gestion_club_backend.Repository.ClubRepository;
import com.example.gestion_club_backend.Repository.ReunionRepository;
import com.example.gestion_club_backend.Service.ReunionService;
import com.example.gestion_club_backend.exception.BlogapiException;
import com.example.gestion_club_backend.exception.ResourceNotFoundException;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import com.example.gestion_club_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReunionServiceImp  implements ReunionService {
    @Autowired
    ReunionRepository reunionRepository;
    @Autowired
    ClubRepository clubRepository;



    @Override
    public ResponseEntity<Reunion> AddReunion(Reunion reunion, UserPrincipal currentUser) {

       return null;
    }

    @Override
    public ResponseEntity<Reunion> getReunion(Long id) {
        return null;
    }

    @Override
    public PagedResponse<Reunion> getAllReunion(int page , int size) {
        return null;
    }

    @Override
    public PagedResponse<Reunion> getAllReunionByUser(int page , int size,UserPrincipal currentUser) {
        return null;
    }

    @Override
    public PagedResponse<Reunion> getAllReunionByClubs(int page , int size,Long id) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Club club = clubRepository.findById(id).orElseThrow();
        Page<Reunion> ReunionPage = reunionRepository.findAllByClubIsContaining(club,pageable);
        return getReunionPagedResponse(ReunionPage);
    }



    @Override
    public ResponseEntity<ApiResponse> DeleteReunion(Long id, UserPrincipal userPrincipal) {
        Reunion Reunion_old = reunionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reunion_STR", "id", id));
        if ( userPrincipal.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            reunionRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted Reunion"), HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION");
    }


    private PagedResponse<Reunion> getReunionPagedResponse(Page<Reunion> ReunionPage) {
        List<Reunion> ReunionList = Arrays.asList(ReunionPage.getContent().toArray(new Reunion[0]));
        if (ReunionPage.getTotalElements()==0){
            return  new PagedResponse<>(Collections.emptyList(),ReunionPage.getNumber(),ReunionPage.getSize(),ReunionPage.getTotalElements() , ReunionPage.getTotalPages(),ReunionPage.isLast());
        }
        return  new PagedResponse<>(ReunionList,ReunionPage.getNumber(),ReunionPage.getSize(),ReunionPage.getTotalElements() , ReunionPage.getTotalPages(),ReunionPage.isLast());
    }

}
