package com.example.Librarymanagementsystem.controller;


import javax.validation.Valid;

import com.example.Librarymanagementsystem.payload.request.SignInRequestDTO;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.AuthenticationResponse;
import com.example.Librarymanagementsystem.payload.response.MessageResponse;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.security.jwt.JwtUtils;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import com.example.Librarymanagementsystem.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Endpoints for user authentication and registration")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final UserService userService;


  @PostMapping("/sign-in")
  @Operation(summary = "Authenticate user", description = "Authenticates a user and returns a JWT token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully authenticated user"),
          @ApiResponse(responseCode = "400", description = "Invalid email or password"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<?> authenticateUser(@RequestBody @Valid SignInRequestDTO loginRequest) {
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


  @PostMapping("/sign-up")
  @Operation(summary = "Register new user", description = "Registers a new user and returns a success message")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "User registered successfully"),
          @ApiResponse(responseCode = "400", description = "Email is already in use"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Response<String>> registerUser(@RequestBody @Validated UserRequestDTO userRequestDTO) {
    Response<String> response = userService.createUser(userRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


  @PreAuthorize("isAuthenticated()")
  @PostMapping("/sign-out")
  @Operation(summary = "Logout user", description = "Logs out the authenticated user and invalidates the JWT token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully logged out user"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've Been Signed Out!"));
  }

}
