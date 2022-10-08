package com.example.gestion_club_backend.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Referent extends Membre {

    String Fonction;
    String Faculte;

}
