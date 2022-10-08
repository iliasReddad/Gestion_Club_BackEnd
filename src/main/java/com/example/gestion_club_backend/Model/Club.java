package com.example.gestion_club_backend.Model;

import com.example.gestion_club_backend.Model.audit.UserDateAudit;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Club  {
    @Id
    @Column(name = "Club_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
     Long id;

    String club_Name;
    String mission;
    String signature;
    @ManyToMany
    @JoinTable(
            name = "Evenement_Club",
            joinColumns = @JoinColumn(name = "Club_id"),
            inverseJoinColumns = @JoinColumn(name = "Evenement_id"))
    List<Evenement> evenements;

    @OneToMany(mappedBy="club")
    List<Budget> budget;

    @OneToMany(mappedBy = "club")
    List<Reunion> reunion;

    @ManyToMany()
    @JoinTable(
            name = "Membres_Club",
            joinColumns = @JoinColumn(name = "Club_id"),
            inverseJoinColumns = @JoinColumn(name = "Membres_Id"))
    List<Compte> membres;


    public Club( String club_Name, String mission, String signature) {
        this.club_Name = club_Name;
        this.mission = mission;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Club club = (Club) o;
        return id != null && Objects.equals(id, club.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
