package com.api.energymarket.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api.energymarket.authenticate.DetailUserData;
import com.api.energymarket.models.UserModel;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserService userService;

  public UserDetailServiceImpl(UserService userService) {
    this.userService = userService;
  }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
    Optional<UserModel> user = userService.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User [" + username +"] not found");
    }
		return new DetailUserData(user);
	}
  
}
