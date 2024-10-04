package com.example.Librarymanagementsystem.data.model;

import com.example.Librarymanagementsystem.data.model.audit.DateAudit;
import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import com.example.Librarymanagementsystem.validation.PasswordMatches;
import com.example.Librarymanagementsystem.validation.ValidEmail;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@PasswordMatches
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
public class User extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier for the user", example = "1")
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    @Size(max = 40)
    @Schema(description = "First name of the user", example = "John")
    @Setter
    @Getter
    @NonNull
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 40)
    @Schema(description = "Last name of the user", example = "Doe")
    @Setter
    @Getter
    @NonNull
    private String lastName;

    @NotBlank
    @Column(name = "username")
    @Size(max = 15)
    @Schema(description = "Username of the user", example = "johndoe")
    @Setter
    @Getter
    @NonNull
    private String username;

    @NotBlank
    @Size(max = 100)
    @Column(name = "password")
    @Schema(description = "Password for the user account", example = "password123")
    @Getter
    @Setter
    @NonNull
    private String password;

    @Getter
    @Setter
    private String matchingPassword;

    @NotBlank
    @NaturalId
    @Size(max = 40)
    @Column(name = "email")
    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    @Setter
    @Getter
    @NonNull
    @ValidEmail
    private String email;

    @Column(name = "role")
    @Schema(description = "Role of the user in the system", example = "USER", allowableValues = {"ADMIN",
            "MANAGER",
            "USER"}, defaultValue = "USER", nullable = true)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Version
    private  Integer version;

}
