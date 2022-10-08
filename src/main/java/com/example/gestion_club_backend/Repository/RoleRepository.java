package com.example.gestion_club_backend.Repository;

import com.example.gestion_club_backend.Model.role.Role;
import com.example.gestion_club_backend.Model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(RoleName name);
}
