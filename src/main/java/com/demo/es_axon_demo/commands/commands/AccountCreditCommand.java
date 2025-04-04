package com.demo.es_axon_demo.commands.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreditCommand {
    @TargetAggregateIdentifier
    private String accountId;

    private double amount;
    
}
