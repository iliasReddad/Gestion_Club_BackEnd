package com.example.gestion_club_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reunion {
    @Id
    @Column(name = "reunion_id", nullable = false)
    private Long reunion_id;
    private  String but_Reunion;
    private  String Statue;
    @ManyToOne()
    @JsonIgnore
    Club club;
}
