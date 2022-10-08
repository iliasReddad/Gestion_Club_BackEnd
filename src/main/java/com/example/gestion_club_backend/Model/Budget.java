package com.example.gestion_club_backend.Model;


import com.example.gestion_club_backend.Model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Budget extends UserDateAudit {
    @Id
    @Column(name = "budget_id", nullable = false)
    private Long budget_id;
    @ManyToOne
    @JoinColumn(name="club_id", nullable=false)
    @JsonIgnore
    Club club;
    float budget;


}
