package com.demo.es_axon_demo.queries.handlers;

import com.demo.es_axon_demo.queries.dto.GetAllAccounts;
import com.demo.es_axon_demo.queries.entities.Account;
import com.demo.es_axon_demo.queries.repositories.AccountOperationRepository;
import com.demo.es_axon_demo.queries.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AountQueryHandler {

    private AccountRepository accountRepository;

    private AccountOperationRepository accountOperationRepository;

    public AountQueryHandler(AccountRepository accountRepository, AccountOperationRepository accountOperationRepository) {
        this.accountRepository = accountRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccounts allAccounts){
        return accountRepository.findAll();
    }
}
