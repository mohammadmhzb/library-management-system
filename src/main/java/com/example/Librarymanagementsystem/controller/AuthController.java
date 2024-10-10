package com.example.Librarymanagementsystem.controller;


import javax.validation.Valid;

import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.payload.request.SignInRequest;
import com.example.Librarymanagementsystem.payload.request.SignUpRequest;
import com.example.Librarymanagementsystem.payload.response.AuthenticationResponse;
import com.example.Librarymanagementsystem.payload.response.MessageResponse;
import com.example.Librarymanagementsystem.security.jwt.JwtUtils;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtUtils jwtUtils;

  /**
   * Endpoint for authenticating users.
   *
   * @param loginRequest The SignInRequest object containing user credentials
   * @return ResponseEntity with AuthenticationResponse and RefreshToken if authentication is successful
   */
  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String token = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return ResponseEntity.ok(new AuthenticationResponse(
            token,
            userDetails.getId(),
            userDetails.getEmail(),
            roles
    ));
  }

  /**
   * Endpoint for registering new users.
   *
   * @param signUpRequest The SignUpRequest object containing user details
   * @return ResponseEntity with a MessageResponse indicating successful registration
   *         or an error message if email is already in use
   */
  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User();
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setRole(UserRole.valueOf(signUpRequest.getRole().toUpperCase()));
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setUsername(signUpRequest.getUsername());

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User Registered Successfully!"));
  }

  /**
   * Endpoint for logging out users.
   *
   * @return ResponseEntity with a MessageResponse indicating successful logout
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've Been Signed Out!"));
  }

}
