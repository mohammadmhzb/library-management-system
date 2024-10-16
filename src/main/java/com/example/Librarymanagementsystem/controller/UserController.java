package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;
import com.example.Librarymanagementsystem.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "USER API", description = "CRUD operations for users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get one user by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<UserResponseDTO>> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user (PUT)", description = "Update all fields of an existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> updateUser(@Validated @RequestBody UserRequestDTO userRequestDTO,
                                                       @Parameter(description = "ID of the user to be updated")
                                                       @PathVariable Long id){
        return new ResponseEntity<>(userService.updateUser(id, userRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update specific fields of a user (PATCH)", description = "Update specific fields of an existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> patchUser(@Validated @RequestBody UserRequestDTO userRequestDTO,
                                                      @Parameter(description = "ID of the user to be updated")
                                                      @PathVariable Long id){
        return new ResponseEntity<>(userService.patchUser(id, userRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ResponseEntity<Response<List<User>>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a user", description = "Remove a users from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);

    }

}