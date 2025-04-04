package com.demo.es_axon_demo.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.demo.es_axon_demo.commands.commands.AccountCreateCommand;
import com.demo.es_axon_demo.commands.commands.AccountCreditCommand;
import com.demo.es_axon_demo.commands.commands.AccountDebitCommand;
import com.demo.es_axon_demo.enums.AccountStatus;
import com.demo.es_axon_demo.events.AccountActivatedEvent;
import com.demo.es_axon_demo.events.AccountCreatedEvent;
import com.demo.es_axon_demo.events.AccountCreditedEvent;
import com.demo.es_axon_demo.events.AccountDebitedEvent;

import lombok.Getter;

@Aggregate
@Getter
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private double initialBalance;

    private AccountStatus status;

    private String currency;

    public  AccountAggregate(){}

    @CommandHandler
    public AccountAggregate(AccountCreateCommand accountCreateCommand){

        AggregateLifecycle.apply(new AccountCreatedEvent(
            accountCreateCommand.getAccountId(),
            accountCreateCommand.getInitialBalance(),
            AccountStatus.CREATED,
            accountCreateCommand.getCurrency()
        ));

        AggregateLifecycle.apply(new AccountActivatedEvent(
            accountCreateCommand.getAccountId(),
            AccountStatus.ACTIVED
        ));

    }

    

    @CommandHandler
    public void debitHandler(AccountDebitCommand accountDebitCommand){

        AggregateLifecycle.apply(new AccountDebitedEvent(
            accountDebitCommand.getAccountId(),
            accountDebitCommand.getAmount()
        ));
    }

    @CommandHandler
    public void credit(AccountCreditCommand accountCreditCommand){
        AggregateLifecycle.apply(new AccountCreditedEvent(
            accountCreditCommand.getAccountId(),
            accountCreditCommand.getAmount()
        ));
    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId=accountCreatedEvent.getAccountId();
        this.initialBalance=accountCreatedEvent.getInitialBalance();
        this.currency=accountCreatedEvent.getCurrency();
        this.status=accountCreatedEvent.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.accountId=accountActivatedEvent.getAccountId();
        this.status=accountActivatedEvent.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.initialBalance=-accountDebitedEvent.getAmount();
        this.accountId=accountDebitedEvent.getAccountId();
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.initialBalance=+accountCreditedEvent.getAmount();
        this.accountId=accountCreditedEvent.getAccountId();
    }
    
}
