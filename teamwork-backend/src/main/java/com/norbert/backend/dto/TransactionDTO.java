package com.norbert.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norbert.backend.entity.OrderType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("employees")
    private List<EmployeeDTO> employees;

    @JsonProperty("order_type")
    private OrderType orderType;

    @JsonProperty("date")
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDTO that)) return false;
        return Objects.equals(getId(), that.getId()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
