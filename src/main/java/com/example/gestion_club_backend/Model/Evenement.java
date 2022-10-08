package com.example.gestion_club_backend.Model;

import com.example.gestion_club_backend.Model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Evenement extends UserDateAudit {
    @Id
    @Column(name = "Evenement_id", nullable = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long event_id;
    String theme;
    String Desciption;
    String Image;
    String DesciptionDetaille;
    String Article;
    int Estimation_Budget;
    String Etat;

    @ManyToMany(mappedBy = "evenements")
    @JsonIgnore
    List<Club> clubAssociés;



    public Evenement(String theme, String desciption, String image, String desciptionDetaille, String article) {
        this.theme = theme;
        Desciption = desciption;
        Image = image;
        DesciptionDetaille = desciptionDetaille;
        Article = article;
    }
}
