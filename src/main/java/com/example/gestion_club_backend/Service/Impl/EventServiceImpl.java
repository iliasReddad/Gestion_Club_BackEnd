package com.example.gestion_club_backend.Service.Impl;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Model.Evenement;
import com.example.gestion_club_backend.Model.role.RoleName;
import com.example.gestion_club_backend.Repository.ClubRepository;
import com.example.gestion_club_backend.Repository.CompteRepository;
import com.example.gestion_club_backend.Repository.EvenementRepository;
import com.example.gestion_club_backend.Service.EvenementService;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl  implements EvenementService {
    private static final String Event_STR ="Event" ;
    @Autowired
    EvenementRepository evenementRepository;

    @Autowired
    ClubServiceImpl clubServiceImpl;


    @Autowired
    ClubRepository clubRepository;

    @Autowired
    CompteRepository compteRepository;

    @Override
    public PagedResponse<Evenement> getAllEvents(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Evenement> evenementPage = evenementRepository.findAll(pageable);
        return getEvenementPagedResponse(evenementPage);

    }

    @Override
    public ResponseEntity<Evenement> getEvent(Long id) {
         Evenement evenement = evenementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(Event_STR, "ID", id));

        return null;
    }

    @Override
    public PagedResponse<Evenement> getEventsByClubs(int page, int size,Long id) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Club club = clubRepository.findById(id).orElseThrow(null);
        System.err.println(id);
        return getEvenementPagedResponse(evenementRepository.findAllByClubAssociésIsContaining(club,pageable));
    }
//
//    @Override
//    public PagedResponse<Evenement> getEventsByUser(int page, int size,Long id) {
//        AppUtils.validatePageNumberAndSize(page, size);
//        Pageable pageable = PageRequest.of(page, size);
//        Club club = clubRepository.findById(id).orElseThrow(null);
//        System.err.println(id);
//        return getEvenementPagedResponse(evenementRepository.findAllByClubAssociésIsContaining(club,pageable));
//    }



    private PagedResponse<Evenement> getEvenementPagedResponse(Page<Evenement> evenementPage) {
        List<Evenement> evenementList = Arrays.asList(evenementPage.getContent().toArray(new Evenement[0]));
        if (evenementPage.getTotalElements()==0){
            return  new PagedResponse<>(Collections.emptyList(),evenementPage.getNumber(),evenementPage.getSize(),evenementPage.getTotalElements() , evenementPage.getTotalPages(),evenementPage.isLast());
        }
        return  new PagedResponse<>(evenementList,evenementPage.getNumber(),evenementPage.getSize(),evenementPage.getTotalElements() , evenementPage.getTotalPages(),evenementPage.isLast());
    }

    @Override
    public ResponseEntity<Evenement> CreateEvent(Evenement evenement,  UserPrincipal userPrincipal) {
        Compte user = compteRepository.getUser(userPrincipal);
        List<Evenement> evenements = new ArrayList<Evenement>();
        List<Club> clubs = clubRepository.findAllByMembresIsContaining(user);
        evenement.setClubAssociés(clubs);
        Evenement evenementCree = evenementRepository.save(evenement);
        evenements.add(evenementCree);
        for (Club club : clubs){
            club.setEvenements(evenements);
            clubServiceImpl.UpdateClub(club.getId(),userPrincipal,club );
        }
        return new ResponseEntity<>(evenementCree, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<Evenement> UpdateEvent(Long id, Evenement evenement, UserPrincipal userPrincipal) {
        Evenement Evenement_old = evenementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Event_STR, "id", id));
        Evenement_old.setEtat(evenement.getEtat());
        Evenement_old.setDesciption(evenement.getDesciption());
        Evenement_old.setEstimation_Budget(evenement.getEstimation_Budget());
//        Evenement_old.setClubAssociés(evenement.getClubAssociés());

        Evenement Update_Evenement = evenementRepository.save(Evenement_old);
        return new ResponseEntity<>(Update_Evenement, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> DeleteEvent(Long id, UserPrincipal userPrincipal) {
        Evenement Evenement_old = evenementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Event_STR, "id", id));
        if ( userPrincipal.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_USER.toString()))) {
            clubRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted Event"), HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION");
    }

}
