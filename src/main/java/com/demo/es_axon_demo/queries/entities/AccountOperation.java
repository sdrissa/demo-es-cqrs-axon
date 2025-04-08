package com.demo.es_axon_demo.queries.entities;

import com.demo.es_axon_demo.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant dateOperation;

    private double amount;

    @ManyToOne
    private Account account;

    private String currency;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

}
