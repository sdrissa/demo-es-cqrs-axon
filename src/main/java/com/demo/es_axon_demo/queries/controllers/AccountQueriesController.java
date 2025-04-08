package com.demo.es_axon_demo.queries.controllers;

import com.demo.es_axon_demo.queries.dto.GetAllAccounts;
import com.demo.es_axon_demo.queries.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("account/query")
public class AccountQueriesController {

    private QueryGateway queryGateway;

    public AccountQueriesController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

@GetMapping("/liste")
    public CompletableFuture<List<Account>> getAccounts(){
       return queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
    }
}
