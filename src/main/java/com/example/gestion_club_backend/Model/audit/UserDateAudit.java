package com.example.gestion_club_backend.Model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@JsonIgnoreProperties(
		value = { "createdBY", "updatedBy" },
		allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {
	private static final long serialVersionUID = 1L;
	//We provide @CreatedBy and @LastModifiedBy to capture the user who created or modified the entity
	//The implementation accesses the Authentication object provided by Spring Security and looks up the custom UserDetails instance that I have created in CustomUserDetailsService implementation.
	// We assume here that you are exposing the domain user through the UserDetails implementation but that, based on the Authentication found,

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;
}
