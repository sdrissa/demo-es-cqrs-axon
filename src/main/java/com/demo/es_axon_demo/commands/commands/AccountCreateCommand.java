package com.demo.es_axon_demo.commands.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class AccountCreateCommand {

    @TargetAggregateIdentifier
    private String accountId;

    private double initialBalance;

    private String currency;

    public AccountCreateCommand(){}

    public AccountCreateCommand(String accountId,double initialBalance,String currency){
        this.accountId=accountId;
        this.initialBalance=initialBalance;
        this.currency=currency;
    }
    
}
