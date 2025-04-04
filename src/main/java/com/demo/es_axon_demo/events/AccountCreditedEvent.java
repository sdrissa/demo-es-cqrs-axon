package com.demo.es_axon_demo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreditedEvent {

    private String accountId;

    private double amount;
    
}
