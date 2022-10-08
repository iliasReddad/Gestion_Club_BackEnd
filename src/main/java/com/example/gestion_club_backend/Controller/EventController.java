package com.example.gestion_club_backend.Controller;


import com.example.gestion_club_backend.Model.Club;
import com.example.gestion_club_backend.Model.Evenement;
import com.example.gestion_club_backend.Repository.EvenementRepository;
import com.example.gestion_club_backend.Service.EvenementService;
import com.example.gestion_club_backend.Service.Impl.EventServiceImpl;
import com.example.gestion_club_backend.exception.ResponseEntityErrorException;
import com.example.gestion_club_backend.payload.ApiResponse;
import com.example.gestion_club_backend.payload.ClubRequest;
import com.example.gestion_club_backend.payload.PagedResponse;
import com.example.gestion_club_backend.security.CurrentUser;
import com.example.gestion_club_backend.security.UserPrincipal;
import com.example.gestion_club_backend.utils.AppConstants;
import com.example.gestion_club_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    EvenementRepository evenementRepository;
    @Autowired
    EventServiceImpl eventService;




    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


    @GetMapping
    public PagedResponse<Evenement> getAllEvents(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                 @CurrentUser UserPrincipal currentUser) {
        AppUtils.validatePageNumberAndSize(page, size);
        return eventService.getAllEvents(page, size);
    }

    @GetMapping("/Byclubs/{id}")
    public PagedResponse<Evenement> getAllEventsByclubs(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                        @PathVariable(name = "id") Long id) {
        AppUtils.validatePageNumberAndSize(page, size);
        return  eventService.getEventsByClubs(page,size, Long.valueOf(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEventById(@PathVariable(name = "id") Long id){
        return eventService.getEvent(id);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Evenement> AddEvent(@RequestBody Evenement evenement ,  @CurrentUser UserPrincipal currentUser) {
        return eventService.CreateEvent(evenement,currentUser);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return eventService.DeleteEvent(id,currentUser);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Evenement> UpdateEvent (@PathVariable(name = "id") Long id, @Valid @RequestBody Evenement evenement,
                                                  @CurrentUser UserPrincipal currentUser){
        return eventService.UpdateEvent(id,evenement,currentUser);
    }







}
