package com.example.gestion_club_backend.Controller;


import com.example.gestion_club_backend.Repository.EvenementRepository;
import com.example.gestion_club_backend.Repository.ReunionRepository;
import com.example.gestion_club_backend.Service.Impl.EventServiceImpl;
import com.example.gestion_club_backend.exception.ResponseEntityErrorException;
import com.example.gestion_club_backend.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reunions")
public class ReunionController {

    @Autowired
    ReunionRepository reunionRepository;
    @Autowired
    EventServiceImpl eventService;

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }


}
