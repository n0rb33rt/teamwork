package com.norbert.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "transactions")
@Entity(name = "Transactions")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transactions_id_seq",
            sequenceName = "transactions_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transactions_id_seq"
    )
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "BIGINT"
    )
    private Long id;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
    )
    @JoinTable(
            name = "transaction_employees",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @NotNull
    private List<Employee> employees;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    @NotNull
    private OrderType orderType;

    @JsonProperty("date")
    @NotNull
    private LocalDate date;

    @JsonProperty("paid")
    private Boolean paid;

}
