package com.api.energymarket.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.energymarket.dtos.UserDto;
import com.api.energymarket.models.UserModel;
import com.api.energymarket.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class UserController {
  
  final UserService userService;
  private final PasswordEncoder encoder;

  public UserController(UserService userService, PasswordEncoder encoder) {
    this.userService = userService;
    this.encoder = encoder;
  }

  @PostMapping("/register")
  public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDto userDto) {
    if (Boolean.TRUE.equals(userService.existsByUsername(userDto.getUsername()))) {
      return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
    }
    if (Boolean.TRUE.equals(userService.existsByEmail(userDto.getEmail()))) {
      return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
    }
    var userModel = new UserModel();
    BeanUtils.copyProperties(userDto, userModel);
    userModel.setPassword(encoder.encode(userDto.getPassword()));
    userModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
    userModel.setRole("USER");
    userService.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(pageable));
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> userModelOptional = userService.getUserById(id);
    if (!userModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<Object> deleteUserById(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> userModelOptional = userService.getUserById(id);
    if (!userModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    userService.delete(userModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("User deleted");
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<Object> updateUserById(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDto userDto) {
    Optional<UserModel> userModelOptional = userService.getUserById(id);
    if (!userModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    var userModel = new UserModel();
    BeanUtils.copyProperties(userDto, userModel);
    userModel.setId(id);
    userModel.setCreatedAt(userModelOptional.get().getCreatedAt());
    userModel.setRole(userModelOptional.get().getRole());
    return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
  }
}
