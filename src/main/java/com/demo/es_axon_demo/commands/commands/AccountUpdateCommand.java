package com.demo.es_axon_demo.commands.commands;

import com.demo.es_axon_demo.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccountUpdateCommand {

    private String accountId;

    private AccountStatus status;
}
