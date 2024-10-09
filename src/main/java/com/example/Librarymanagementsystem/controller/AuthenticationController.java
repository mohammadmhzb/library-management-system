package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.payload.request.AuthenticationRequest;
import com.example.Librarymanagementsystem.payload.request.RegisterRequest;
import com.example.Librarymanagementsystem.payload.response.AuthenticationResponse;
import com.example.Librarymanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) throws Throwable {
    return ResponseEntity.ok(service.authenticate(request));
  }


}
