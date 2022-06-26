package com.api.energymarket.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  @Email(message = "Please enter a valid email")
  private String email;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
