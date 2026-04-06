package com.challenge.project02.features.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.project02.features.user.entities.Role;
import com.challenge.project02.features.user.entities.RoleName;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
