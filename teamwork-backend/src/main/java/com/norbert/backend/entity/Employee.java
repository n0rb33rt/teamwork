package com.norbert.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Entity(name = "Employees")
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(name = "check_email", columnNames = {"email"})
})
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
    @Length(min = 2,max = 30)
    private String firstName;

    @JsonProperty("last_name")
    @NotNull
    @NotBlank
    @Length(min = 2,max = 30)
    private String lastName;

    @JsonProperty("email")
    @NotNull
    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
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
