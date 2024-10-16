package com.example.Librarymanagementsystem.data.model;

import com.example.Librarymanagementsystem.data.model.audit.DateAudit;
import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
//@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier for the user", example = "1")
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    @Size(max = 30)
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 30)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @NotBlank
    @Column(name = "username")
    @Size(max = 15)
    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @NotBlank
    @Size(min = 6)
    @Column(name = "password")
    @Schema(description = "Password for the user account", example = "password123")
    private String password;

    @NotBlank
    @Size(min = 11, max = 11, message = "Phone number must be exactly 11 digits")
    @Column(name = "phone_number")
    @Schema(description = "Phone number of the user", example = "09150835353")
    private String phoneNumber;

    @NotBlank
    @NaturalId
    @Size(max = 30)
    @Column(name = "email")
    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Column(name = "role")
    @Schema(description = "Role of the user in the system", example = "USER", allowableValues = {"ADMIN",
            "MANAGER",
            "USER"},
            defaultValue = "USER",
            nullable = true)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Version
    private  Integer version;

}
