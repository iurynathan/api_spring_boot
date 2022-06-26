package com.api.energymarket.repositories;

import com.api.energymarket.models.UserModel;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserModel> findByUsername(String username);
}
