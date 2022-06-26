package com.api.energymarket.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.api.energymarket.models.UserModel;
import com.api.energymarket.repositories.UserRepository;

@Service
public class UserService {
  
  final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserModel save(UserModel userModel) {
    return userRepository.save(userModel);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public Page<UserModel> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public Optional<UserModel> getUserById(UUID id) {
    return userRepository.findById(id);
  }

  @Transactional
  public void delete(UserModel userModel) {
    userRepository.delete(userModel);
  }

  public Optional<UserModel> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
