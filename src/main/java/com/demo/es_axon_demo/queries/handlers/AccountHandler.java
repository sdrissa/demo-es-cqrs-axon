package com.demo.es_axon_demo.queries.handlers;

import com.demo.es_axon_demo.enums.OperationType;
import com.demo.es_axon_demo.events.*;
import com.demo.es_axon_demo.queries.entities.Account;
import com.demo.es_axon_demo.queries.entities.AccountOperation;
import com.demo.es_axon_demo.queries.repositories.AccountOperationRepository;
import com.demo.es_axon_demo.queries.repositories.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

@Component
public class AccountHandler {

    private AccountRepository accountRepository;

    private AccountOperationRepository accountOperationRepository;

    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountHandler(AccountRepository accountRepository, AccountOperationRepository accountOperationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent, EventMessage eventMessage){

        Account account=accountRepository.save(Account.builder()
                        .accountId(accountCreatedEvent.getAccountId())
                        .balance(accountCreatedEvent.getInitialBalance())
                        .currency(accountCreatedEvent.getCurrency())
                        .status(accountCreatedEvent.getStatus())
                        .createdAt(eventMessage.getTimestamp())
                .build());

    }


    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){

        Account account=accountRepository.findById(accountActivatedEvent.getAccountId()).get();
        account.setStatus(accountActivatedEvent.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountUpdatedEvent accountUpdatedEvent){

        Account account=accountRepository.findById(accountUpdatedEvent.getAccountId()).get();
        account.setStatus(accountUpdatedEvent.getStatus());
        accountRepository.save(account);
    }


    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent, EventMessage eventMessage){
        Account account=accountRepository.findById(accountCreditedEvent.getAccountId()).get();
        AccountOperation accountOperation=AccountOperation.builder()
                .operationType(OperationType.CREDIT)
                .account(account)
                .dateOperation(eventMessage.getTimestamp())
                .currency(account.getCurrency())
                .build();

        accountOperationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        queryUpdateEmitter.emit(y->true,accountOperation);

    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent, EventMessage eventMessage){
        Account account=accountRepository.findById(accountDebitedEvent.getAccountId()).get();
        AccountOperation accountOperation=AccountOperation.builder()
                .operationType(OperationType.DEBIT)
                .account(account)
                .dateOperation(eventMessage.getTimestamp())
                .currency(account.getCurrency())
                .build();

        accountOperationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(y->true,accountOperation);

    }

}
