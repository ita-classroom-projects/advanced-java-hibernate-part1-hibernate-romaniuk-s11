package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"myTodos", "otherTodos"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
    @Pattern(
            regexp = "^[A-Z][a-z]+(-[A-Z][a-z]+)*$",
            message = "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"
    )
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
    @Pattern(
            regexp = "^[A-Z][a-z]+(-[A-Z][a-z]+)*$",
            message = "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"
    )
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Must be a valid e-mail address")
    @NotBlank(message = "Must be a valid e-mail address")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/`~\\\\]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters"
    )
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    private List<ToDo> myTodos;

    @ManyToMany(mappedBy = "collaborators")
    private List<ToDo> otherTodos;
}
