package com.demo.es_axon_demo.events;

import com.demo.es_axon_demo.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreatedEvent {

    private String accountId;

    private double initialBalance;

    private AccountStatus status;

    private String currency;
    
}
