package com.example.gestion_club_backend.Model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
public class Membre extends Compte {
    String Filiére;
    Boolean Resident_Campus;


}
