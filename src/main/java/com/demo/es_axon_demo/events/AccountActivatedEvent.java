package com.demo.es_axon_demo.events;

import com.demo.es_axon_demo.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountActivatedEvent {

    private String accountId;

    private AccountStatus status;
    
}
