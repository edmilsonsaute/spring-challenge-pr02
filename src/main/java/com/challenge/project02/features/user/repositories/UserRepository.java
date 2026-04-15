package com.challenge.project02.features.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.project02.features.user.entities.User;


public interface UserRepository extends JpaRepository<User,UUID> {
    
   @EntityGraph(attributePaths = {"roles"})
   Optional<User> findByEmail(String email);
}
