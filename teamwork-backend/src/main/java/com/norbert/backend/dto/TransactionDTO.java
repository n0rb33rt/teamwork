package com.norbert.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norbert.backend.entity.OrderType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime date;
}
