package com.example.gestion_club_backend.Service.Impl;

import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Model.role.RoleName;
import com.example.gestion_club_backend.Repository.ClubRepository;
import com.example.gestion_club_backend.Repository.CompteRepository;
import com.example.gestion_club_backend.Repository.MembreRepository;
import com.example.gestion_club_backend.Service.ClubService;
import com.example.gestion_club_backend.exception.BlogapiException;
import com.example.gestion_club_backend.exception.ResourceNotFoundException;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.ClubRequest;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.UserPrincipal;
import com.example.gestion_club_backend.utils.AppUtils;
import org.modelmapper.ModelMapper;
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
public class ClubServiceImpl implements ClubService {
    private static final String CREATED_AT = "createdAt";
    private static final String CLUB_STR = "Club";

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    MembreRepository membreRepository;

    @Autowired
    ClubRepository clubRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Club> AddClub(ClubRequest clubReq, UserPrincipal currentUser) {
        Compte user = compteRepository.getUser(currentUser);
        Club club = new Club();
        modelMapper.map(clubReq, club);


        List<Compte> membres = new ArrayList<Compte>();
        membres.add(user);
        membres.forEach((membre) -> {
            System.out.println(membre.getId_Compte());
        });
        club.setMembres(membres);
        Club newclub = clubRepository.save(club);
        System.out.println("created");
        return new ResponseEntity<>(newclub, HttpStatus.CREATED);


    }

    @Override
    public ResponseEntity<Club> getClub(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "ID", id));
        return new ResponseEntity<>(club, HttpStatus.OK);

    }



    @Override
    public PagedResponse<Club> getAllClubs(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page,size);
        Page<Club> clubs = clubRepository.findAll(pageable);
        List<Club> clubReponse =  Arrays.asList(clubs.getContent().toArray(new Club[0]));
        System.err.println(clubReponse.size());
        return getClubPagedResponse(clubs, clubReponse);
    }

    private PagedResponse<Club> getClubPagedResponse(Page<Club> clubs, List<Club> clubReponse) {
        if (clubs.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), clubs.getNumber(), clubs.getSize(), clubs.getTotalElements(),
                    clubs.getTotalPages(), clubs.isLast());
        }


        return new PagedResponse<>(clubReponse, clubs.getNumber(), clubs.getSize(), clubs.getTotalElements(), clubs.getTotalPages(),
                clubs.isLast());
    }

    @Override
    public PagedResponse<Club> getAllClubsByUser(int page, int size, UserPrincipal userPrincipal) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page,size);
        Compte user = compteRepository.getUser(userPrincipal);
        Page<Club> clubs = clubRepository.findAllByMembresIsContaining(user,pageable);
        List<Club> clubReponse =  Arrays.asList(clubs.getContent().toArray(new Club[0]));
        return getClubPagedResponse(clubs, clubReponse);
    }

    @Override
    public ResponseEntity<Club> UpdateClub(Long id, UserPrincipal currentUser, Club club) {
        return null;
    }

    @Override
    public ResponseEntity<Club> UpdateClub(Long id, UserPrincipal currentUser, ClubRequest clubReq) {
        Club club = new Club();
        modelMapper.map(clubReq, club);
        Club club_old = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "id", id));
//        if (currentUser.getAuthorities()
//                .containsAll(new SimpleGrantedAuthority(RoleName.ROLE_President.toString())) || new SimpleGrantedAuthority(RoleName.ROLE_VicePresident.toString())) ) {
            if ( !club.getClub_Name().isEmpty() ){
                club_old.setClub_Name(club.getClub_Name());

            }
            if (!club.getMission().isEmpty()){
                club_old.setMission(club.getMission());
            }
            if(!club.getSignature().isEmpty()){
                club_old.setSignature(club.getSignature());

            }
            Club Update_Club = clubRepository.save(club_old);
            return new ResponseEntity<>(Update_Club, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<ApiResponse> DeleteClub(Long id, UserPrincipal currentUser) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "id", id));
        Compte user = compteRepository.getUser(currentUser);
        if ( currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_USER.toString()))) {
            clubRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted Club"), HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION");    }



    @Override
    public ResponseEntity<ApiResponse> JoinClub(Long id, UserPrincipal currentUser) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "id", id));
        Compte user = compteRepository.getUser(currentUser);
        if(CheckJoinClub(id,currentUser)){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "You Already Joined the Club"), HttpStatus.IM_USED);
        }
        else{
            List<Compte> membres1;
            membres1=club.getMembres();
            membres1.add(user);
            club.setMembres(membres1);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully Joined the Club"), HttpStatus.OK);
        }
       }

    @Override
    public Boolean CheckJoinClub(Long id, UserPrincipal currentUser) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "id", id));
        Compte user = compteRepository.getUser(currentUser);
        if (club.getMembres().contains(user)){
            System.err.print("already joined");
            return Boolean.TRUE;

        }
        else{
            return Boolean.FALSE;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> LeaveClub(Long id, UserPrincipal currentUser) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLUB_STR, "id", id));
        Compte user = compteRepository.getUser(currentUser);
            List<Compte> membres1;
            membres1=club.getMembres();
            membres1.remove(user);
            club.setMembres(membres1);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully leaved the Club"), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<ApiResponse> DeleteAllClub( UserPrincipal currentUser) {
        if ( currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_USER.toString()))) {
            clubRepository.deleteAll();
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted all album"), HttpStatus.OK);
        }
        throw new BlogapiException(HttpStatus.UNAUTHORIZED, "YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION");    }


    @Override
    public PagedResponse<Club> getUserClubs(String username, int page, int size) {
        return null;
    }
}
