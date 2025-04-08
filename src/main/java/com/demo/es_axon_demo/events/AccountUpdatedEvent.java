package com.demo.es_axon_demo.events;

import com.demo.es_axon_demo.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccountUpdatedEvent {

    private String accountId;

    private AccountStatus status;
}
