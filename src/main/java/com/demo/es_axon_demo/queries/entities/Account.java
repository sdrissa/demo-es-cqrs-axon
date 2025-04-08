package com.demo.es_axon_demo.queries.entities;

import com.demo.es_axon_demo.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private String accountId;

    private double balance;

    private Instant createdAt;

    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
