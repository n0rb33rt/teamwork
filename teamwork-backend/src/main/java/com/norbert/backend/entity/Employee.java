package com.norbert.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Entity(name = "Employees")
@Table(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employees_id_seq",
            sequenceName = "employees_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employees_id_seq"
    )
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "BIGINT"
    )
    @JsonProperty("id")
    private Long id;

    @JsonProperty("first_name")
    @NotNull
    @NotBlank
    private String firstName;

    @JsonProperty("last_name")
    @NotNull
    @NotBlank
    private String lastName;

    @JsonProperty("email")
    @NotNull
    @NotBlank
    private String email;

    @JsonProperty("card_number")
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @JsonProperty("working")
    private Boolean working;


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
