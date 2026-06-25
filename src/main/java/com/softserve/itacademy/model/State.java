package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "tasks")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_seq")
    @SequenceGenerator(name = "state_seq", sequenceName = "states_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @NotBlank(message = "State name cannot be empty")
    @Size(min = 1, max = 20, message = "State name must be between 1 and 20 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9_\\- ]+$",
            message = "State name can only contain Latin letters, numbers, dashes, spaces, and underscores"
    )
    private String name;

    @OneToMany(mappedBy = "state")
    private List<Task> tasks;
}