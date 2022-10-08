package com.example.gestion_club_backend.Controller;
import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Compte;
import com.example.gestion_club_backend.Repository.CompteRepository;
import com.example.gestion_club_backend.Service.Impl.ClubServiceImpl;
import com.example.gestion_club_backend.exception.ResponseEntityErrorException;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.ClubRequest;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.CurrentUser;
import com.example.gestion_club_backend.security.UserPrincipal;
import com.example.gestion_club_backend.utils.AppConstants;
import com.example.gestion_club_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;




@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    ClubServiceImpl clubService;
    @Autowired
    ClubServiceImpl clubServiceImpl;

    @Autowired
    CompteRepository compteRepository;


    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }



    @GetMapping
    public PagedResponse<Club> getAllClubs(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @CurrentUser UserPrincipal currentUser) {
        AppUtils.validatePageNumberAndSize(page, size);

        return clubService.getAllClubsByUser(page, size,currentUser);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Compte> getUser(@PathVariable(name = "id") String id, @CurrentUser UserPrincipal currentUser) {
        Compte compte = null;
        System.out.println(id);

        if (id.equals("admin")){
             compte = compteRepository.getUser(currentUser);
            System.out.println("Principale");
        }
        else{
             compte = compteRepository.findByUsername(id).orElse(null);
            System.out.println("simple user");

        }
        System.err.println(compte.getFirstName());

        System.err.println(compte);
        return new ResponseEntity<>(compte, HttpStatus.OK);
    }

    @GetMapping("/JoinClub/{id}")
    public ResponseEntity<ApiResponse> JoinClub(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return clubServiceImpl.JoinClub(id,currentUser);
    }
    @GetMapping("/CheckJoinClub/{id}")
    public Boolean CheckJoinClub(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return clubServiceImpl.CheckJoinClub(id,currentUser);
    }

    @GetMapping("/LeaveClub/{id}")
    public ResponseEntity<ApiResponse> LeaveClub(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return clubServiceImpl.LeaveClub(id,currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@PathVariable(name = "id") Long id) {
        return clubService.getClub(id);
    }

  @PostMapping
  public ResponseEntity<Club> AddClub( @RequestBody ClubRequest clubRequest , @CurrentUser UserPrincipal currentUser) {
      System.out.println(clubRequest.toString());
      return clubServiceImpl.AddClub(clubRequest,currentUser);

  }



    @PostMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Club> UpdateClub(@PathVariable(name = "id") Long id, @Valid @RequestBody ClubRequest clubReq,
                                                     @CurrentUser UserPrincipal currentUser) {
        System.out.println("updat");

        System.out.println(clubReq.toString());
        return clubServiceImpl.UpdateClub(id,currentUser,clubReq );
    }


    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteClub(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return clubServiceImpl.DeleteClub(id,currentUser);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAllClub( @CurrentUser UserPrincipal currentUser) {
        return clubServiceImpl.DeleteAllClub(currentUser);
    }



}
