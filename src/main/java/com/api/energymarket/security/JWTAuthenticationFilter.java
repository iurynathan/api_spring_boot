package com.api.energymarket.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.energymarket.authenticate.DetailUserData;
import com.api.energymarket.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public static final int TOKEN_EXPIRATION_TIME = 864000000;

  public static final String SECRET_KEY = "secret";

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException("Fail is attemptAuthentication", e);
    }
  }

  @Override
  protected void successfulAuthentication(
  HttpServletRequest request,
  HttpServletResponse response,
  FilterChain chain,
  Authentication authResult
  ) throws IOException, ServletException {
    DetailUserData user = (DetailUserData) authResult.getPrincipal();

    String token = JWT.create()
      .withSubject(user.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SECRET_KEY));

    response.getWriter().write(token);
    response.getWriter().flush();
  }
}
